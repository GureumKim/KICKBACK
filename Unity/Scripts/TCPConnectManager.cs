using System;
using System.IO;
using System.Text;
using System.Net.Sockets;
using System.Collections;
using System.Collections.Generic;

using TMPro;
using UnityEngine;
using UnityEngine.UI;
using System.Text.RegularExpressions;

public class TCPConnectManager : MonoBehaviour
{
    public static TCPConnectManager Instance = null;

    [Header("Chat")]
    [SerializeField] private TMP_Text MessageElement; // 채팅 메세지
    [SerializeField] private GameObject LobbyChattingList; // 로비 채팅 리스트
    [SerializeField] private TMP_InputField LobbyChat; // 로비 입력 메세지
    [SerializeField] private Button LobbyChatSendBtn; // 채널 메세지 전송 버튼

    [Header("Connect")]
    private TcpClient _tcpClient;
    private NetworkStream _networkStream;
    private StreamWriter writer;

    // 호스트
    private string hostname = "192.168.100.146"; // 로컬 호스트
    private int port = 1370;

    private void Awake()
    {
        // 싱글톤
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(this);
        }
        else
        {
            // 기존 데이터 instance로 복사
            Instance.MessageElement = MessageElement;
            Instance.LobbyChattingList = LobbyChattingList;
            Instance.LobbyChat = LobbyChat;
            Instance.LobbyChatSendBtn = LobbyChatSendBtn;
        }


        // 리스너 붙이기
        Instance.LobbyChatSendBtn.onClick.RemoveAllListeners();
        Instance.LobbyChatSendBtn.onClick.AddListener(() => Instance.MessageSendBtnClicked(LobbyChat));
    }

    void Start()
    {
        ConnectToServer();
        LobbyChat.characterLimit = 20;
    }

    void Update()
    {
        // 데이터가 들어온 경우
        while (_networkStream != null && _networkStream.DataAvailable)
        {
            string response = ReadMessageFromServer();
            //DispatchResponse(response);
        }

        if (Input.GetKeyDown(KeyCode.Return) || Input.GetKeyDown(KeyCode.KeypadEnter))
        {
            MessageSendBtnClicked(LobbyChat);
        }
    }

    //#region 요청 분배하기
    //private void DispatchResponse(string response)
    //{
    //    string type = getType(response);

    //    if (type == "0101")
    //    {
    //        showMessage(response, LobbyChattingList);
    //    }
    //}

    //private string getType(string response)
    //{
    //    string[] words = response.Split('\"');
    //    return words[3];
    //}
    //#endregion

    #region 서버 연결
    public void ConnectToServer()
    {
        string message = "Hello Server";

        try
        {
            // TCP 서버에 연결
            _tcpClient = new TcpClient(hostname, port);
            _networkStream = _tcpClient.GetStream();
            writer = new StreamWriter(_networkStream);

            string json = "{" +
                    "\"userName\":\"0100101010101\"," +
                    $"\"message\": \"{message}\"" +
                "}";

            SendMessageToServer(json);

            Debug.Log("ConnectToServer");
        }
        catch (Exception e)
        {
            // 연결 중 오류 발생 시
            Debug.Log($"Failed to connect to the server: {e.Message}");
        }
    }

    // 서버로 메세지 보내기
    private void SendMessageToServer(string message)
    {
        if (_tcpClient == null) return;

        writer.WriteLine(message);
        writer.Flush(); // 메세지 즉각 전송
    }

    // 서버에세 메세지 읽기
    private string ReadMessageFromServer()
    {
        if (_tcpClient == null) return null;
        try
        {
            StringBuilder message = new StringBuilder();
            BinaryReader reader = new BinaryReader(_networkStream, Encoding.UTF8);
            
            while (_networkStream.DataAvailable)
            {
                char readChar = reader.ReadChar();
                if (readChar == '\n') break; // '\n' 를 구분자로 사용
                
                message.Append(readChar);
            }
            return message.ToString();
        }
        catch(Exception e)
        {
            Debug.Log("응답 읽기 실패 : " + e.Message);
            return null;
        }
    }
    #endregion

    #region 채팅 관련
    // 메세지 전송 버튼 클릭 시
    public void MessageSendBtnClicked(TMP_InputField inputField)
    {
        Debug.Log("메시지 전송");

        string message = inputField.text;

        if (message == "")
        {
            return;
        }


        string json = "{" +
                "\"userName\":\"0100101010101\"," +
                $"\"message\": \"{message}\"" +
            "}";
        Debug.Log(json);
        inputField.text = "";
        SendMessageToServer(json);
        inputField.Select();
        inputField.ActivateInputField();
        showMessage(json, LobbyChattingList);
    }

    // 메세지 들어왔을 때
    public void showMessage(string message, GameObject ChatScrollView)
    {
        // 붙일 부모 오브젝트
        Transform content = ChatScrollView.transform.Find("Viewport/Content");

        ChatMessage chatMessage = JsonUtility.FromJson<ChatMessage>(message);

        TMP_Text temp1 = Instantiate(MessageElement);

        temp1.text = $"{chatMessage.UserName} : {chatMessage.Message}";
        temp1.transform.SetParent(content, false);


        // 20개 넘어가면 채팅 위에서부터 지우기
        if (content.childCount >= 20)
        {
            Destroy(content.GetChild(1).gameObject);
        }

        StartCoroutine(ScrollToBottom(ChatScrollView));
    }

    // 스크롤 맨 아래로 내리기
    IEnumerator ScrollToBottom(GameObject ChatScrollView)
    {
        // 다음 프레임 기다림
        yield return null;

        Transform content = ChatScrollView.transform.Find("Viewport/Content");

        // LayOut Gropu을 강제로 즉시 업데이트
        LayoutRebuilder.ForceRebuildLayoutImmediate((RectTransform)content);

        // 스크롤 맨 아래로 내림
        ChatScrollView.GetComponent<ScrollRect>().verticalNormalizedPosition = 0f;
    }

    #endregion
}