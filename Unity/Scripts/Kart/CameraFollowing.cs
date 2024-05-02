using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraFollowing : MonoBehaviour
{
    [SerializeField] private LapController controller;

    public Vector3 offset;
    [SerializeField] private Vector3 finishCamOffset; // Inspector���� ���� ������ ���� ī�޶� offset

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
            // ��Ⱑ ������ ��, offset�� finishCamOffset���� ������ ����
            offset = Vector3.Lerp(offset, finishCamOffset, Time.deltaTime);

            // ī�޶� ��ġ�� ����� offset�� ����Ͽ� ������Ʈ
            transform.position = player.position + offset;

            // ī�޶��� Y���� �������� 180�� ȸ���� ������ ����
            Quaternion currentRotation = transform.rotation;
            Quaternion targetRotation = Quaternion.Euler(0, 180, 0); // Y���� �������� 180�� ȸ��

            transform.rotation = Quaternion.Slerp(currentRotation, targetRotation, Time.deltaTime);
        }
    }
}
