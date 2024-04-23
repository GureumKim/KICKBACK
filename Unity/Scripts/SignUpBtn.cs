using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SignUpBtn : MonoBehaviour
{
    [SerializeField]
    private GameObject SignUpPanel;

    void Start()
    {
        SignUpPanel.SetActive(false);
    }

    // SignUpPanel 의 SetActive 여부에 따라 끄고 켜기
    public void SignUp()
    {
        if (!SignUpPanel.activeSelf)
        {
            SignUpPanel.SetActive(true);
        }
        else if (SignUpPanel.activeSelf)
        {
            SignUpPanel.SetActive(false);
        }
    }
}
