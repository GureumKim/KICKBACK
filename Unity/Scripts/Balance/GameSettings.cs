using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu (fileName = "GameSettings", menuName = "GameBalance/Settings/GameSettings")]
public class GameSettings : MonoBehaviour
{
    public MeasurementSystem EnumMeasurementSystem;
    public Layer LayerForAiDetection;
    public List<VehicleController> AvailableVehicles = new List<VehicleController>();

    public static bool IsMobilePlatform
    {
        get
        {
#if UNITY_EDITOR
            return UnityEditor.EditorUserBuildSettings.activeBuildTarget == UnityEditor.BuildTarget.Android ||
                UnityEditor.EditorUserBuildSettings.activeBuildTarget == UnityEditor.BuildTarget.iOS;
#else
            return Application.isMobilePlatform;
#endif
        }
    }
}
