using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.SceneManagement;

public class WaitingRoomManager : MonoBehaviour
{
    [Header("Map")]
    public List<Sprite> sprites = new List<Sprite>();
    public TMP_Dropdown dropdown;
    public Image mapImage;

    [Header("User")]
    public List<TMP_Text> nicknames;
    public List<Image> avatars;
    public GameObject selectCharacters;
    public List<Button> characterButtons;
    public List<Image> checkMarks;

    [Header("Script")]
    [SerializeField] private BusinessManager businessManager;
    [SerializeField] private DataManager dataManager;
    [SerializeField] private GameObject LobbyCanvas;
    private User loginUserInfo;

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
        selectCharacters.SetActive(false);
    }

    void Update()
    {
        nickNameUpdate();
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
        dataManager.roomUserList = null;
        dataManager.cnt = 0;
    }

    private void nickNameUpdate()
    {
        if (dataManager.roomUserList != null)
        {
            for (int i = 0; i < dataManager.roomUserList.Count; i++)
            {
                nicknames[i].text = dataManager.roomUserList[i];
            }
        }
    }

    public void PopUp()
    {
        if (!selectCharacters.activeSelf)
        {
            selectCharacters.SetActive(true);
        }
        else if (selectCharacters.activeSelf)
        {
            selectCharacters.SetActive(false);

            for (int i = 0; i < checkMarks.Count; i++)
            {
                checkMarks[i].gameObject.SetActive(false);
            }
        }
    }

    // 버튼 클릭 시 호출될 메소드. 인덱스를 매개변수로 받음
    public void ClickCharacter(int buttonIndex)
    {
        // 모든 체크마크를 먼저 비활성화
        for (int i = 0; i < checkMarks.Count; i++)
        {
            checkMarks[i].gameObject.SetActive(false);
        }

        // 클릭된 버튼에 해당하는 체크마크만 활성화
        checkMarks[buttonIndex].gameObject.SetActive(true);
    }
}
