using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using static GroundDetection;

[System.Serializable]
public class GroundConfig
{
    public string Caption;
    public GroundType GroundType;

    public ParticleSystem IdleParticles; // ���� �� �⺻���� ��ƼŬ �ý���
    public ParticleSystem SlipParticles; // ǥ�鿡�� �̲����� �� �ߵ��ϴ� ��ƼŬ �ý���
    public bool TemperatureDependent; // Ÿ�̾� �µ��� ���� �۵��ϴ� ������ ��ƼŬ �ý���
    public bool SpeedDependent; // �ڵ��� ���� �ӵ��� ���� �۵��ϴ� ������ ��ƼŬ �ý���

    public float WheelStiffness; // wheel ���� ������
}

// GetGroundConfig method�� �����ϱ� ���� Abstract class
public abstract class IGroundEntity : MonoBehaviour
{
    public abstract GroundConfig GetGroundConfig(Vector3 position);
}