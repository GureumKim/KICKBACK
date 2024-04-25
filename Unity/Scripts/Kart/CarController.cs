using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(Rigidbody))]
public class CarController : MonoBehaviour
{
    [Header("CarController")]

    public float SteerWheelMaxAngle; // �����̸� ���� ���ư��� �ִ� ���� (Visual Only)
    public Transform SteerWheel;

    float SteerWheelStartXangle; // ��Ƽ� �� ���� X ����

    public ICarControl CarControl { get; set; } // ICarControl�� īƮ ��Ʈ��
    public bool BlockControl { get; protected set; } // Blocks Input

    protected override void Awake()
    {
        base.Awake();

    }
}
