using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.SceneManagement;

public class FootBallResultManager : MonoBehaviour
{
    [SerializeField] private TMP_Text ResultScore;

    private IEnumerator ScoreUpdate()
    {
        string url = "https://k10c209.p.ssafy.io/api/v1"; // ��û URL
        string requestUrl = url + "/record/updateSpeedRecord2";

        string jsonRequestBody = "";

        if (SceneManager.GetActiveScene().name == "Cebu Track")
        {
            jsonRequestBody = "{" +
                $"\"mapName\":\"Cebu\"," +
                $"\"time\":\"{ResultScore.text}\"" +
                "}";
        }
        else if (SceneManager.GetActiveScene().name == "Mexico Track")
        {
            jsonRequestBody = "{" +
                $"\"mapName\":\"Mexico\"," +
                $"\"time\":\"{ResultScore.text}\"" +
                "}";
        }
        else if (SceneManager.GetActiveScene().name == "Downhill Track")
        {
            jsonRequestBody = "{" +
                $"\"mapName\":\"Downhill\"," +
                $"\"time\":\"{ResultScore.text}\"" +
                "}";
        }

        byte[] bodyRaw = System.Text.Encoding.UTF8.GetBytes(jsonRequestBody);

        // ��û ����
        using (UnityWebRequest request = new UnityWebRequest(requestUrl, "PUT"))
        {
            request.uploadHandler = new UploadHandlerRaw(bodyRaw);
            request.downloadHandler = new DownloadHandlerBuffer();
            request.SetRequestHeader("Content-Type", "application/json");
            request.SetRequestHeader("Authorization", DataManager.Instance.accessToken);
            yield return request.SendWebRequest();

            // ��û ���� ��
            if (request.result == UnityWebRequest.Result.Success)
            {
                Debug.Log("��Ÿ�� ���� ����");
            }
            // ��û ���� ��
            else
            {
                Debug.Log("��Ÿ�� ���� ����");
                yield break;
            }
        }
    }

    public void GotoRoom()
    {
        SceneManager.LoadScene("Room");
        StartCoroutine(ScoreUpdate());
    }
}
