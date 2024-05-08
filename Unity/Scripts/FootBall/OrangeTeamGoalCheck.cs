using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class OrangeTeamGoalCheck : MonoBehaviour
{
    public TextMeshProUGUI OrangeScore;
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
            OrangeScore.text = score.ToString();
        }
    }
}
