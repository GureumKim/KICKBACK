using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraFollowing : MonoBehaviour
{
    [SerializeField] private LapController controller;

    public Vector3 offset;
    [SerializeField] private Vector3 finishCamOffset; // Inspector에서 설정 가능한 최종 카메라 offset

    public Transform player;

    private PlayerScript playerScript;

    public Vector3 originCamPos;

    // Start is called before the first frame update
    void Start()
    {
        playerScript = player.GetComponent<PlayerScript>();
    }

    void LateUpdate()
    {
        if (!controller.isFinish)
        {
            transform.position = player.position + offset;
            transform.GetChild(0).localPosition = Vector3.Lerp(transform.GetChild(0).localPosition, originCamPos, 3 * Time.deltaTime);
        }
        else
        {
            // 경기가 끝났을 때, offset을 finishCamOffset으로 서서히 변경
            offset = Vector3.Lerp(offset, finishCamOffset, Time.deltaTime);

            // 카메라 위치를 변경된 offset을 사용하여 업데이트
            transform.position = player.position + offset;

            // 카메라의 Y축을 기준으로 180도 회전을 서서히 적용
            Quaternion currentRotation = transform.rotation;
            Quaternion targetRotation = Quaternion.Euler(0, 180, 0); // Y축을 기준으로 180도 회전

            transform.rotation = Quaternion.Slerp(currentRotation, targetRotation, Time.deltaTime);
        }
    }
}
