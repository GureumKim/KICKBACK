using System;
using System.IO;
using System.Text;
using System.Net.Sockets;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

using MessagePack;
using Highlands.Server;

public class TCPConnectManager : MonoBehaviour
{
    public static TCPConnectManager Instance = null;

    [Header("Chat")]
    [SerializeField] private TMP_Text MessageElement; // ä�� �޼���
    [SerializeField] private GameObject LobbyChattingList; // �κ� ä�� ����Ʈ
    [SerializeField] private TMP_InputField LobbyChat; // �κ� �Է� �޼���
    [SerializeField] private Button LobbyChatSendBtn; // ä�� �޼��� ���� ��ư

    [Header("Connect")]
    private TcpClient _tcpClient;
    private NetworkStream _networkStream;
    private StreamWriter writer;
    private User loginUserInfo;

    // ȣ��Ʈ
    private string hostname = "localhost"; // ���� ȣ��Ʈ
    private int port = 1371;
    // private string hostname = "k10c209.p.ssafy.io"; // ���� ȣ��Ʈ
    // private int port = 1370;

    private void Awake()
    {
        // �̱���
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(this);
        }
        else
        {
            // ���� ������ instance�� ����
            Instance.MessageElement = MessageElement;
            Instance.LobbyChattingList = LobbyChattingList;
            Instance.LobbyChat = LobbyChat;
            Instance.LobbyChatSendBtn = LobbyChatSendBtn;
        }


        // ������ ���̱�
        Instance.LobbyChatSendBtn.onClick.RemoveAllListeners();
        Instance.LobbyChatSendBtn.onClick.AddListener(() => Instance.MessageSendBtnClicked(LobbyChat));
    }

    void Start()
    {
        LobbyChat.characterLimit = 20;
    }

    void Update()
    {
        // �����Ͱ� ���� ���
        while (_networkStream != null && _networkStream.DataAvailable)
        {
            Debug.Log("incoming");
            ReadMessageFromServer();
        }
        
        if (Input.GetKeyDown(KeyCode.Return) || Input.GetKeyDown(KeyCode.KeypadEnter))
        {
            MessageSendBtnClicked(LobbyChat);
        }
    }
    
    //#region ��û �й��ϱ�
    //private void DispatchResponse(string response)
    //{
    //    string type = getType(response);

    //    if (type == "0101")
    //    {
    // showMessage(response, LobbyChattingList);
    //    }
    //}

    //private string getType(string response)
    //{
    //    string[] words = response.Split('\"');
    //    return words[3];
    //}
    // #endregion

    #region ���� ����
    public void ConnectToServer()
    {
        string greeting = "Hello Server";


        try
        {
            // TCP ������ ����
            _tcpClient = new TcpClient(hostname, port);
            _networkStream = _tcpClient.GetStream();
            writer = new StreamWriter(_networkStream);
            loginUserInfo = DataManager.Instance.loginUserInfo;
            
            Message message = new Message
            {
                command = 2,
                channelIndex = 0,
                userName = "test",
                message = greeting
            };

            var bytes = MessagePackSerializer.Serialize(message);
             
            SendMessageToServer(bytes);

            Debug.Log("ConnectToServer");
        }
        catch (Exception e)
        {
            // ���� �� ���� �߻� ��
            Debug.Log($"Failed to connect to the server: {e.Message}");
        }
    }

    // ������ �޼��� ������
    private void SendMessageToServer(byte[] message)
    {
        if (_tcpClient == null) return;

        _networkStream.Write(message, 0, message.Length);
        _networkStream.Flush();
    }

    // �����κ��� ������ �޼��� �б�
    private void ReadMessageFromServer()
    {
        if (_tcpClient == null || !_tcpClient.Connected) return;
        try
        {
            if (_networkStream == null)
            {
                _networkStream = _tcpClient.GetStream();
            }

            StringBuilder message = new StringBuilder();
        
            // ��Ʈ��ũ ��Ʈ���� �����Ͱ� ���� ������ �ݺ�
            while (_networkStream.DataAvailable)
            {
                byte[] buffer = new byte[_tcpClient.ReceiveBufferSize];
                int bytesRead = _networkStream.Read(buffer, 0, buffer.Length); // ���� �����͸� ����

                if (bytesRead > 0)
                {
                    // MessagePackSerializer�� ����Ͽ� �޽��� ������ȭ
                    Message receivedMessage = MessagePackSerializer.Deserialize<Message>(buffer.AsSpan().Slice(0, bytesRead).ToArray());

                    // ���ŵ� �޽����� StringBuilder�� �߰�
                    // message.Append(receivedMessage.userName + ": " + receivedMessage.message + "\n");
                    
                    showMessage(receivedMessage.userName, receivedMessage.message, LobbyChattingList);

                }
            }
        }
        catch(Exception e)
        {
            Debug.Log("���� �б� ���� : " + e.Message);
        }
    }
    
    #endregion



    #region ä�� ����
    // �޼��� ���� ��ư Ŭ�� ��
    public void MessageSendBtnClicked(TMP_InputField inputField)
    {
        Debug.Log("send message");

        string message = inputField.text;

        if (message == "")
        {
            return;
        }

        Message pack = new Message
        {
            command = 2,
            channelIndex = 0,
            userName = "test",
            message = message
        };

        var msgpack = MessagePackSerializer.Serialize(pack);
        
        inputField.text = "";
        SendMessageToServer(msgpack);
        inputField.Select();
        inputField.ActivateInputField();
    }

    // �޼��� ������ ��
    public void showMessage(string userName, string message, GameObject ChatScrollView)
    {
        // ���� Ȯ�� �� �޽��� ����
        // if (userName === )
        // {}

        String chat = userName + ": " + message;
        
        // ���� �θ� ������Ʈ
        Transform content = ChatScrollView.transform.Find("Viewport/Content");
        TMP_Text temp1 = Instantiate(MessageElement);

        temp1.text = chat;
        temp1.transform.SetParent(content, false);


        // 20�� �Ѿ�� ä�� ���������� �����
        if (content.childCount >= 20)
        {
            Destroy(content.GetChild(1).gameObject);
        }

        StartCoroutine(ScrollToBottom(ChatScrollView));
    }

    // ��ũ�� �� �Ʒ��� ������
    IEnumerator ScrollToBottom(GameObject ChatScrollView)
    {
        // ���� ������ ��ٸ�
        yield return null;

        Transform content = ChatScrollView.transform.Find("Viewport/Content");

        // LayOut Gropu�� ������ ��� ������Ʈ
        LayoutRebuilder.ForceRebuildLayoutImmediate((RectTransform)content);

        // ��ũ�� �� �Ʒ��� ����
        ChatScrollView.GetComponent<ScrollRect>().verticalNormalizedPosition = 0f;
    }

    #endregion

    #region ���� ����

    // ���� ��
    public void OnApplicationQuit()
    {
        clearChat();

        DataManager.Instance.gameDataClear();

        // tcp ���� ����
        if (_tcpClient != null)
        {
            DisconnectFromServer();
        }
        writer = null;
        loginUserInfo = null;
    }

    public void DisconnectFromServer()
    {
        // ���� ����
        _networkStream.Close();
        _tcpClient.Close();
        _networkStream = null;
        _tcpClient = null;
    }

    public void clearChat()
    {
        Transform content;

        if (LobbyChattingList != null)
        {
            content = LobbyChattingList.transform.Find("Viewport/Content");
            foreach (Transform child in content)
            {
                Destroy(child.gameObject);
            }
        }
    }

    #endregion
}