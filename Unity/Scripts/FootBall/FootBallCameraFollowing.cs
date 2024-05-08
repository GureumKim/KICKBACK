using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class FootBallCameraFollowing : MonoBehaviour
{
    [SerializeField] private FootBallCountDownController countDownController;

    public Vector3 offset;

    [SerializeField] private Vector3 introCamOffset1; // ��Ʈ�� ���� ���� ù��° ī�޶� offset
    [SerializeField] private Vector3 introCamOffset2; // ��Ʈ�� ���� ���� �ι�° ī�޶� offset

    [SerializeField] private Vector3 finishCamOffset; // Inspector���� ���� ������ ���� ī�޶� offset

    public Transform player;

    public Vector3 originCamPos;

    public bool isStarting = false; // ��Ʈ�� �� ���� ����

    void Awake()
    {
        StartCoroutine(IntroSequence());
    }

    IEnumerator IntroSequence()
    {
        isStarting = true; // ��Ʈ�� �� ����

        // ī�޶� introCamOffset1 ��ġ�� ��� �̵�
        transform.position = player.position + introCamOffset1;
        // ī�޶��� ȸ���� Y�� ���� 180���� ����
        transform.rotation = Quaternion.Euler(0, 180, 0);

        // introCamOffset1���� introCamOffset2�� ������ �̵�
        float startTime = Time.time;
        float journeyLength = Vector3.Distance(introCamOffset1, introCamOffset2);
        float journeyTime = 5.0f; // �̵��� �ɸ��� �ð�
        float fractionOfJourney = 0;

        while (fractionOfJourney < 1)
        {
            float distCovered = (Time.time - startTime) * journeyLength / journeyTime;
            fractionOfJourney = distCovered / journeyLength;
            transform.position = player.position + Vector3.Lerp(introCamOffset1, introCamOffset2, fractionOfJourney);
            yield return null;
        }

        // introCamOffset2���� ������ ķ ��ġ�� �̵��ϱ� ���� 3�ʰ� ���
        yield return new WaitForSeconds(2.0f);

        // introCamOffset2���� originCamPos�� ��� �̵�
        transform.position = player.position + originCamPos;

        // ī�޶��� ȸ���� �ٽ� 0���� ����
        transform.rotation = Quaternion.Euler(0, 0, 0);

        countDownController.gameObject.SetActive(true);
        StartCoroutine(countDownController.StartGame());
    }


    void LateUpdate()
    {
        if (countDownController.gameObject.activeSelf)
        {
            transform.position = player.position + offset;
            transform.GetChild(0).localPosition = Vector3.Lerp(transform.GetChild(0).localPosition, originCamPos, 3 * Time.deltaTime);
        }
    }
}
