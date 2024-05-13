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

        // 드롭다운의 선택 변화에 대한 리스너 추가
        dropdown.onValueChanged.AddListener(delegate 
        {
            ChangeImage(dropdown.value);
        });

    }


    void ChangeImage(int index)
    {
        // 선택된 인덱스에 해당하는 스프라이트로 이미지 변경
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
