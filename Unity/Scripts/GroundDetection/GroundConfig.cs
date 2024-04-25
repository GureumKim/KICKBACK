using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using static GroundDetection;

[System.Serializable]
public class GroundConfig
{
    public string Caption;
    public GroundType GroundType;

    public ParticleSystem IdleParticles; // 주행 시 기본적인 파티클 시스템
    public ParticleSystem SlipParticles; // 표면에서 미끄러질 때 발동하는 파티클 시스템
    public bool TemperatureDependent; // 타이어 온도에 의해 작동하는 의존성 파티클 시스템
    public bool SpeedDependent; // 자동차 주행 속도에 의해 작동하는 의존성 파티클 시스템

    public float WheelStiffness; // wheel 마찰 제곱수
}

// GetGroundConfig method를 구현하기 위한 Abstract class
public abstract class IGroundEntity : MonoBehaviour
{
    public abstract GroundConfig GetGroundConfig(Vector3 position);
}