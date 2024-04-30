using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraFollowing : MonoBehaviour
{
    public Vector3 offset;
    public Transform player;

    private PlayerScript playerScript;

    public Vector3 originCamPos;
    public Vector3 boostCamPos;

    // Start is called before the first frame update
    void Start()
    {
        playerScript = player.GetComponent<PlayerScript>();
    }

    // Update is called once per frame
    void LateUpdate()
    {
        transform.position = player.position + offset;

        if (playerScript.BoostTime > 0)
        {
            transform.GetChild(0).localPosition = Vector3.Lerp(transform.GetChild(0).localPosition, boostCamPos, 3 * Time.deltaTime);
        }
        else
        {
            transform.GetChild(0).localPosition = Vector3.Lerp(transform.GetChild(0).localPosition, originCamPos, 3 * Time.deltaTime);
        }
    }
}
