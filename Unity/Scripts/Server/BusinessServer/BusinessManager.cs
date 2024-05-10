using System;
using System.IO;
using System.Net.Sockets;
using System.Text;
using Highlands.Server;
using Highlands.Server.BusinessServer;
using MessagePack;
using UnityEngine;
using Message = Highlands.Server.BusinessServer.Message;

public class BusinessManager : MonoBehaviour
{
    public static BusinessManager Instance = null;

    private TcpClient _tcpClient;
    private NetworkStream _networkStream;
    private StreamWriter writer;
    private User loginUserInfo;

    private string hostname = "localhost";
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
        }
    }

    void Start()
    {
    }

    void Update()
    {
        while (_networkStream != null && _networkStream.DataAvailable)
        {
            Debug.Log("B.S: Incoming");
            ReadMessageFromServer();
        }
    }

    public void ConnectToServer()
    {
        try
        {
            _tcpClient = new TcpClient(hostname, port);
            _networkStream = _tcpClient.GetStream();
            writer = new StreamWriter(_networkStream);
            loginUserInfo = DataManager.Instance.loginUserInfo;

            var message = new Message
            {
                command = Command.CLIENT
            };

            var bytes = MessagePackSerializer.Serialize(message);
             
            SendMessageToServer(bytes);

            Debug.Log("ConnectToServer");
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
    
    private void SendMessageToServer(byte[] message)
    {
        if (_tcpClient == null) return;

        _networkStream.Write(message, 0, message.Length);
        _networkStream.Flush();
    }
    
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
        
            // 네트워크 스트림에 데이터가 있을 때까지 반복
            while (_networkStream.DataAvailable)
            {
                byte[] buffer = new byte[_tcpClient.ReceiveBufferSize];
                int bytesRead = _networkStream.Read(buffer, 0, buffer.Length); // 실제 데이터를 읽음

                if (bytesRead > 0)
                {
                    // MessagePackSerializer를 사용하여 메시지 역직렬화
                    Message receivedMessage = MessagePackSerializer.Deserialize<Message>(buffer.AsSpan().Slice(0, bytesRead).ToArray());

                    // 수신된 메시지를 StringBuilder에 추가
                    // message.Append(receivedMessage.userName + ": " + receivedMessage.message + "\n");
                    
                }
            }
        }
        catch(Exception e)
        {
            Debug.Log("응답 읽기 실패 : " + e.Message);
        }
    }
    
    public void OnApplicationQuit()
    {

        DataManager.Instance.gameDataClear();

        // tcp 연결 관련
        if (_tcpClient != null)
        {
            DisconnectFromServer();
        }
        writer = null;
        loginUserInfo = null;
    }
    
    public void DisconnectFromServer()
    {
        // 연결 종료
        _networkStream.Close();
        _tcpClient.Close();
        _networkStream = null;
        _tcpClient = null;
    }
}