using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class LobbyManager : MonoBehaviour
{
    [SerializeField] private GameObject LoginCanvas; // 로그인 캔버스
    [SerializeField] private GameObject LobbyCanvas; // 로비 캔버스
    [SerializeField] private Button LogoutBtn; // 로그 아웃 버튼

    void Start()
    {
        TCPConnectManager.Instance.ConnectToServer();
        BusinessManager.Instance.ConnectToServer();

        // 로그인 캔버스와 로비 캔버스를 태그로 찾아서 할당
        LoginCanvas = GameObject.FindWithTag("Login Canvas");
        LobbyCanvas = GameObject.FindWithTag("Lobby Canvas");


        // 씬에서 로그아웃 버튼을 찾아서 버튼 컴포넌트를 할당
        GameObject logoutButtonObject = GameObject.Find("Logout Btn");
        if (logoutButtonObject != null)
        {
            LogoutBtn = logoutButtonObject.GetComponent<Button>();

            // 로그아웃 버튼에 클릭 리스너 추가
            if (LogoutBtn != null)
            {
                LogoutBtn.onClick.RemoveAllListeners(); // 기존 리스너 제거
                LogoutBtn.onClick.AddListener(LobbyOut); // 새로운 리스너 추가
            }
        }
    }

    public void LobbyOut()
    {
        SceneManager.LoadScene("Login");

        // 로그인 캔버스 활성화, 로비 캔버스 비활성화
        if (LoginCanvas != null)
            LoginCanvas.SetActive(true);
        if (LobbyCanvas != null)
            LobbyCanvas.SetActive(false);

        // 기타 초기화 작업 수행
        TCPConnectManager.Instance.OnApplicationQuit();
        BusinessManager.Instance.OnApplicationQuit();
        DataManager.Instance.dataClear();
    }
}
