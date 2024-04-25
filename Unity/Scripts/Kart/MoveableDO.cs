using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveableDO : MonoBehaviour
{
    protected Vector3 InitialPos;

    public float Health = 100;

    public Vector3 LocalCenterPoint { get; private set; }
    bool LocalCenterPointIsCalculated;
    public bool IsInited { get; private set; }
    public bool IsDead { get { return Health <= 0; } }
    public float InitHealth { get; private set; }
    public float HealthPercent { get; private set; }

    MeshFilter _MeshFilter;
    public MeshFilter MeshFilter
    {
        get
        {
            if (!_MeshFilter)
            {
                _MeshFilter = GetComponent<MeshFilter>();
            }
            return _MeshFilter;
        }
    }

    public virtual void Awake()
    {
        LocalCenterPoint = CalculateLocalCenterPoint();

        InitialPos = transform.localPosition;
    }

    public virtual void MoveObject(Vector3 damageVelocity)
    {
        transform.localPosition += damageVelocity;
    }

    public virtual void RetoreObject()
    {
        var healthDiff = InitHealth - Health;
        if (healthDiff != 0)
        {
            Health = InitHealth;
            HealthPercent = 1;
        }
    }

    // MeshFilter를 활용하여 중앙 지점 계산 / 초기화 되었을 땐 Vector3.zero로 세팅
    public Vector3 CalculateLocalCenterPoint()
    {
        if (LocalCenterPointIsCalculated)
        {
            return LocalCenterPoint;
        }

        LocalCenterPointIsCalculated = true;

        if (!MeshFilter)
        {
            return Vector3.zero;
        }

        Vector3 sum = Vector3.zero;
        foreach (var vert in MeshFilter.sharedMesh.vertices)
        {
            sum += vert;
        }
        return sum / MeshFilter.sharedMesh.vertices.Length;
    }

    public virtual void InitDamageObject()
    {
        if (!IsInited)
        {
            IsInited = true;
            InitHealth = Health;
            HealthPercent = 1;
        }
    }
}
