using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class MakingRoomPopUp : MonoBehaviour
{
    [SerializeField] private GameObject m_Room;
    [SerializeField] private TMP_InputField roomName;
    [SerializeField] private TMP_InputField roomPwd;
    public List<Button> modeButtons;
    public List<Image> checkMarks;
    public List<TMP_Text> modeTxts;
    public string modeName;

    void Start()
    {
        m_Room.SetActive(false);
    }

    public void OpenPopUp()
    {
        roomName.text = "";
        roomPwd.text = "";
        m_Room.SetActive(true);
    }

    public void CloseBtn()
    {  
        roomName.text = "";
        roomPwd.text = "";
        m_Room.SetActive(false);

        // ��� üũ��ũ�� ���� ��Ȱ��ȭ
        for (int i = 0; i < checkMarks.Count; i++)
        {
            checkMarks[i].gameObject.SetActive(false);
        }
    }

    // ��ư Ŭ�� �� ȣ��� �޼ҵ�. �ε����� �Ű������� ����
    public void ClickMode(int buttonIndex)
    {
        // ��� üũ��ũ�� ���� ��Ȱ��ȭ
        for (int i = 0; i < checkMarks.Count; i++)
        {
            checkMarks[i].gameObject.SetActive(false);
        }

        // Ŭ���� ��ư�� �ش��ϴ� üũ��ũ�� Ȱ��ȭ
        checkMarks[buttonIndex].gameObject.SetActive(true);
        // ��� �̸� �Ҵ�
        modeName = modeTxts[buttonIndex].text;
    }
}
