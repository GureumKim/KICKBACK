using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class BlueTeamGoalCheck : MonoBehaviour
{
    public TextMeshProUGUI BlueScore;
    private int score;

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
        }
    }
}
