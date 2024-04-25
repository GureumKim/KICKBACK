using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu (fileName = "ResourceSettings", menuName = "GameBalance/Settings/ResourcesSettings")]
public class ResourcesSettings : MonoBehaviour
{
    public PlayerController PlayerControllerPrefab;
    public PlayerController PlayerControllerPrefab_ForMobile;
    public Camera UVCMainCamera;
    public GroundDetection DefaultGroundDetection;
}
