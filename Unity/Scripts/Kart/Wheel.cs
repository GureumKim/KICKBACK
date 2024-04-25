using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(WheelCollider))]
public class Wheel : MoveableDO
{
    [Range(-1f, 1f)]
    public float SteerPercent; // wheel turns 의 퍼센트, 1 - CarController.Steer.MaxSteerAngle 의 가능한 최대 값, -1 : 반대 wheel turn
    public bool DriveWheel;
    public float MaxBrakeTorque;
    public bool HandBrakeWheel;
    public Transform WheelView; // wheel이 갖는 position, rotation의 transform
    public Transform WheelHub; // wheel의 Y axis rotation의 transform
    public float MaxVisualDamageAngle = 5f;

    [Range(0, 1)]
    public float AntiRollBar;

    public Wheel AntiRolWheel;

    [Tooltip("Suspension 극점에서 기울여지는 wheel의 각도 (Only visual effect")]
    public float MaxSuspensionWheelAngle;
    [Tooltip("Suspension이 종속적이라면 wheel의 각도는 반대쪽 whell에 의존 (Only Visual effect)")]
    public bool DependentSuspension;

    public float RPM { get { return WheelCollider.rpm; } }
    public float CurrentMaxSlip { get { return Mathf.Max(CurrentForwardSlip, CurrentSidewaysSlip); } }
    public float CurrentForwardSlip { get; private set; }
    public float CurrentSidewaysSlip { get; private set; }
    public float SlipNormalized { get; private set; }
    public float ForwardSlipNormalized { get; private set; }
    public float SidewaySlipNormalized { get; private set; }
    public float SuspensionPos { get; private set; } = 0;
    public float PrevSuspensionPos { get; private set; } = 0;
    public float SuspensionPosDiff { get; private set; } = 0;
    public float WheelTemperature { get; private set; } // 타이어 스모크 visualizing 을 위한 온도
    public bool HasForwardSlip { get { return CurrentForwardSlip > WheelCollider.forwardFriction.asymptoteSlip; } }
    public bool HasSlideSlip { get { return CurrentSidewaysSlip > WheelCollider.sidewaysFriction.asymptoteSlip; } }
    public WheelHit GetHit { get { return Hit; } }
    public Vector3 HitPoint { get; private set; }
    public bool IsGrounded { get { return !IsDead && WheelCollider.isGrounded; } }
    public float StopEmitFx { get; set; }
    public float Radius { get { return WheelCollider.radius; } }
    public Vector3 LocalPositionOnAwake { get; private set; } // 차 상태를 위함
    public bool IsSteeringWheel { get { return !Mathf.Approximately(0, SteerPercent); } }

    Transform[] ViewChilds;
    Dictionary<Transform, Quaternion> InitialChildRotations = new Dictionary<Transform, Quaternion>();
    Transform InitialParent;
    public WheelCollider WheelCollider { get; protected set; }

    [System.NonSerialized]
    public Vector3 Position;
    [System.NonSerialized]
    public Quaternion Rotation;

    Vector3 LocalPosition;

    protected VehicleController Vehicle;
    protected WheelHit Hit;
    GroundConfig DefaultGroundConfig { get { return GroundDetection.GetDefaultGroundConfig; } }
    protected float CurrentRotateAngle;

    const float TemperatureChangeSpeed = 0.1f;
    float GroundStiffness;
    float BrakeSpeed = 2;
    float CurrentBrakeTorque;

    GroundConfig _CurrentGroundConfig;

    // 그라운드가 바꼈을 때 wheel의 grip 이 변경
    public GroundConfig CurrentGroundConfig
    {
        get
        {
            return _CurrentGroundConfig;
        }
        set
        {
            if (_CurrentGroundConfig != value)
            {
                _CurrentGroundConfig = value;
                if (_CurrentGroundConfig != null)
                {
                    GroundStiffness = _CurrentGroundConfig.WheelStiffness;
                }
            }
        }
    }

    public override void Awake()
    {
        Vehicle = GetComponent<VehicleController>();
        if (Vehicle == null)
        {
            Debug.LogError("[Wheel] Parents without CarController");
            Destroy(this);
        }

        WheelCollider = GetComponent<WheelCollider>();
        WheelCollider.ConfigureVehicleSubsteps(40, 100, 20);

        LocalPositionOnAwake = transform.localPosition;
        InitialPos = transform.localPosition;
        InitDamageObject();

        ViewChilds = new Transform[WheelView.childCount];
        
        for (int i = 0; i < ViewChilds.Length; i++)
        {
            ViewChilds[i] = WheelView.GetChild(i);
            InitialChildRotations.Add(ViewChilds[i], ViewChilds[i].localRotation);
        }

        InitialParent = WheelView.parent;
        CurrentGroundConfig = DefaultGroundConfig;
    }
}