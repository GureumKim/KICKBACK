using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(WheelCollider))]
public class Wheel : MoveableDO
{
    [Range(-1f, 1f)]
    public float SteerPercent; // wheel turns �� �ۼ�Ʈ, 1 - CarController.Steer.MaxSteerAngle �� ������ �ִ� ��, -1 : �ݴ� wheel turn
    public bool DriveWheel;
    public float MaxBrakeTorque;
    public bool HandBrakeWheel;
    public Transform WheelView; // wheel�� ���� position, rotation�� transform
    public Transform WheelHub; // wheel�� Y axis rotation�� transform
    public float MaxVisualDamageAngle = 5f;

    [Range(0, 1)]
    public float AntiRollBar;

    public Wheel AntiRolWheel;

    [Tooltip("Suspension �������� ��￩���� wheel�� ���� (Only visual effect")]
    public float MaxSuspensionWheelAngle;
    [Tooltip("Suspension�� �������̶�� wheel�� ������ �ݴ��� whell�� ���� (Only Visual effect)")]
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
    public float WheelTemperature { get; private set; } // Ÿ�̾� ����ũ visualizing �� ���� �µ�
    public bool HasForwardSlip { get { return CurrentForwardSlip > WheelCollider.forwardFriction.asymptoteSlip; } }
    public bool HasSlideSlip { get { return CurrentSidewaysSlip > WheelCollider.sidewaysFriction.asymptoteSlip; } }
    public WheelHit GetHit { get { return Hit; } }
    public Vector3 HitPoint { get; private set; }
    public bool IsGrounded { get { return !IsDead && WheelCollider.isGrounded; } }
    public float StopEmitFx { get; set; }
    public float Radius { get { return WheelCollider.radius; } }
    public Vector3 LocalPositionOnAwake { get; private set; } // �� ���¸� ����
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

    // �׶��尡 �ٲ��� �� wheel�� grip �� ����
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