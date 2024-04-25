using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(Rigidbody))]
public class CarController : MonoBehaviour
{
    [Header("CarController")]

    public float SteerWheelMaxAngle; // 스터이링 휠이 돌아가는 최대 각도 (Visual Only)
    public Transform SteerWheel;

    float SteerWheelStartXangle; // 스티어링 휠 시작 X 각도

    public ICarControl CarControl { get; set; } // ICarControl로 카트 컨트롤
    public bool BlockControl { get; protected set; } // Blocks Input

    protected override void Awake()
    {
        base.Awake();

    }
}
