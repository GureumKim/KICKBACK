using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class BlueTeamGoalCheck : MonoBehaviour
{
    public TextMeshProUGUI BlueScore;
    private int score;
    public AudioSource audioSource;
    public ParticleSystem[] particleSystems; // 파티클 시스템 배열 추가

    void Start()
    {
        score = 0;
    }
    private void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Ball"))
        {
            score++;
            BlueScore.text = score.ToString();
            audioSource.Play();
            PlayAllParticles(); // 파티클 재생 함수 호출
        }
    }

    void PlayAllParticles()
    {
        foreach (var ps in particleSystems)
        {
            ps.Play();
        }
    }
}
