using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(Rigidbody))]
public class VehicleController : MonoBehaviour
{
    [Header("VehicleController")]

    [SerializeField] bool ShowBoundsGizmo = false;

    public string Vehicle;
    public Wheel[] Wheels = new Wheel[0];
    
}
