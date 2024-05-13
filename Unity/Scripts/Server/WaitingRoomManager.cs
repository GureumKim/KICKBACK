using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.SceneManagement;

public class WaitingRoomManager : MonoBehaviour
{
    public List<Sprite> sprites = new List<Sprite>();
    public TMP_Dropdown dropdown;
    public Image mapImage;

    [SerializeField] private BusinessManager businessManager;
    [SerializeField] private DataManager dataManager;
    [SerializeField] private GameObject LobbyCanvas;

    void Awake()
    {
        businessManager = FindObjectOfType<BusinessManager>();
        dataManager = FindObjectOfType<DataManager>();

        LobbyCanvas = GameObject.Find("Lobby Canvas");
    }

    private void OnEnable()
    {
        LobbyCanvas.SetActive(false);
    }

    void Start()
    {

        // ��Ӵٿ��� ���� ��ȭ�� ���� ������ �߰�
        dropdown.onValueChanged.AddListener(delegate 
        {
            ChangeImage(dropdown.value);
        });

    }


    void ChangeImage(int index)
    {
        // ���õ� �ε����� �ش��ϴ� ��������Ʈ�� �̹��� ����
        mapImage.sprite = sprites[index];
    }

    public void LeaveRoom()
    {
        businessManager.jlrRoom(Highlands.Server.Command.LEAVE, dataManager.channelIndex);

        LobbyCanvas.SetActive(true);

        RoomClear();

        SceneManager.LoadScene("Lobby");
    }

    public void RoomClear()
    {
        dataManager.channelIndex = -1;
        dataManager.channelName = "";
    }
}
