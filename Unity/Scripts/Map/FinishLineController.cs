using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FinishLineController : MonoBehaviour
{
    [SerializeField] private LapController controller;
    private void OnTriggerExit(Collider other)
    {
        controller.UpdateLap();
        StartCoroutine(controller.Finish());
    }
}
