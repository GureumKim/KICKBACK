using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using TMPro;

public class LobbyManager : MonoBehaviour
{
    [SerializeField] private GameObject LoginCanvas; // �α��� ĵ����
    [SerializeField] private GameObject LobbyCanvas; // �κ� ĵ����
    [SerializeField] private Button LogoutBtn; // �α� �ƿ� ��ư
    
    [Header("Lobby")]
    public GameObject ScrollViewLobbyList;  // ��� ���� ����Ʈ ���� ��ũ�Ѻ�
    public GameObject UserListElement;      // ���� ����Ʈ�� �� ���� ������Ʈ
    public Button LobbyAllListBtn;          // ��� ���� ����Ʈ ���� ��ư

    [Header("Room")]
    public GameObject ScrollViewChannelList;    // ä�� ����Ʈ
    public GameObject ChannelListElement;       // ä�� ����Ʈ �׸�
    public ChannelListElement SelectedChannel;  // ���õ� ä�� ����Ʈ

    void Start()
    {
        TCPConnectManager.Instance.ConnectToServer();
        BusinessManager.Instance.ConnectToServer();
        BusinessManager.Instance.LobbyManagerScript = gameObject.GetComponent<LobbyManager>();
        
        // �α��� ĵ������ �κ� ĵ������ �±׷� ã�Ƽ� �Ҵ�
        LoginCanvas = GameObject.FindWithTag("Login Canvas");
        LobbyCanvas = GameObject.FindWithTag("Lobby Canvas");
        ScrollViewLobbyList = GameObject.Find("User List");
        ScrollViewChannelList = GameObject.Find("Room List");

        // ������ �α׾ƿ� ��ư�� ã�Ƽ� ��ư ������Ʈ�� �Ҵ�
        GameObject logoutButtonObject = GameObject.Find("Logout Btn");
        if (logoutButtonObject != null)
        {
            LogoutBtn = logoutButtonObject.GetComponent<Button>();

            // �α׾ƿ� ��ư�� Ŭ�� ������ �߰�
            if (LogoutBtn != null)
            {
                LogoutBtn.onClick.RemoveAllListeners(); // ���� ������ ����
                LogoutBtn.onClick.AddListener(LobbyOut); // ���ο� ������ �߰�
            }
        }
    }

    public void LobbyOut()
    {
        SceneManager.LoadScene("Login");

        // �α��� ĵ���� Ȱ��ȭ, �κ� ĵ���� ��Ȱ��ȭ
        if (LoginCanvas != null)
            LoginCanvas.SetActive(true);
        if (LobbyCanvas != null)
            LobbyCanvas.SetActive(false);

        // ��Ÿ �ʱ�ȭ �۾� ����
        TCPConnectManager.Instance.OnApplicationQuit();
        BusinessManager.Instance.OnApplicationQuit();
        DataManager.Instance.dataClear();
    }
    
    // ��ü ���� �ҷ�����
    public void getAllUsers(List<string> userList)
    {
        if(userList != null)
        {
            // ���� ����Ʈ �� ��ũ�Ѻ�(������ �θ�)
            Transform content = ScrollViewLobbyList.transform.Find("All Users Scroll View/Viewport/Content");
            // ���� ����Ʈ ����
            // TODO: ������Ʈ Ǯ��
            foreach(Transform child in content) {
                Destroy(child.gameObject);
            }

            // ���� ����Ʈ�� �� ������ ������Ʈ ����
            GameObject[] userListElements = new GameObject[userList.Count];

            // ���� ����Ʈ ������ ������Ʈ�� �� ������
            //GameObject[] activeUserData = new GameObject[userList.Length];
            for (int i = 0; i < userList.Count; i++)
            {
                // �ڱ� �ڽ��� �ǳʶٱ�
                // if(userList[i] == DataManager.Instance.loginUserInfo.NickName)   // chk
                // {
                //     continue;
                // }
                // ��� ���� �� �θ� ����
                userListElements[i] = Instantiate(UserListElement);
                userListElements[i].transform.SetParent(content, false);
                // ���� ������ �ʱ�ȭ
                UserListElement userListElementScript = userListElements[i].GetComponent<UserListElement>();
                userListElementScript.Nickname = userList[i];
                // ������ ���̱�
                userListElementScript.UserNameText.text = userList[i];
            }

            // ���� ���� �ο� �ؽ�Ʈ ������Ʈ
            // ConnectedUserCount.text = "���� �ο�: " + userList.Length;
        }
    }
    
    [Serializable]
    class ChannelInfo
    {
        public int roomIndex;   // �� ��ȣ
        public string roomName; // �� ����
        public string mapName; // �� �̸�
        public bool isOnGame; // ���������� �ƴ���
        public int roomUser; // ��� ����������
    }
    
    // �� ����Ʈ �ҷ�����
    public void getRoomList(List<string> roomList)
    {
        // ä�� ����Ʈ �� ��ũ�Ѻ�(������ �θ�)
        Transform content = ScrollViewChannelList.transform.Find("Scroll View/Viewport/Content");
        // ���� ����Ʈ ����
        // TODO: ������Ʈ Ǯ��
        foreach (Transform child in content)
        {
            Destroy(child.gameObject);
        }

        for(int i = 0; i < roomList.Count; i++)
        {   
            
            // ���� �ο� ������ �Ⱥ��̰� �ϱ�
            // if(channelList.data[i].isOnGame || channelList.data[i].cnt == 6)
            // {
            //     continue;
            // }

            //Debug.Log("���̸�: " + channelList.data[i].channelName);
            // ������ �����
            ChannelInfo roomInfo;
            if (i != roomList.Count - 1)
            {
                roomInfo = JsonUtility.FromJson<ChannelInfo>(roomList[i] + "}");
            }
            else
            {
                roomInfo = JsonUtility.FromJson<ChannelInfo>(roomList[i]);
            }
            Debug.Log(roomInfo.roomUser + "���� �� �ο���");
            GameObject channelListElement = Instantiate(ChannelListElement);
            ChannelListElement channelListElementScript = channelListElement.GetComponent<ChannelListElement>();
            channelListElementScript.RoomIndex = roomInfo.roomIndex;
            channelListElementScript.RoomName = roomInfo.roomName;
            channelListElementScript.mapName = roomInfo.mapName;
            channelListElementScript.roomUser = roomInfo.roomUser;
            channelListElementScript.IsOnGame = roomInfo.isOnGame;
            
            // �θ� ���̱�
            channelListElement.transform.SetParent(content, false);
        }
    }
    
    
    //
    // // �� ����� ��ư Ŭ�� ��
    // public void CreateChannelBtnClicked()
    // {
    //     CreateChannel.SetActive(true);
    // }
    //
    // // �� ������ư Ŭ�� ��
    // public void JoinChannelBtnClicked()
    // {
    //     TCPConnectManager.Instance.joinChannel();   // chk
    // }
}
