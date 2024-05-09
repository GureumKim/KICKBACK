using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class VirtualJoyStick : MonoBehaviour, IBeginDragHandler, IDragHandler, IEndDragHandler
{
    [SerializeField] private RectTransform lever;
    private RectTransform rectTransform;

    [SerializeField, Range(10, 150)] private float leverRange;

    public Vector2 inputDirection;
    private bool isInput;

    [SerializeField] private PlayerScript playerScript;

    private void Awake()
    {
        rectTransform = GetComponent<RectTransform>();
    }

    public void OnBeginDrag(PointerEventData eventData)
    {
        ControlJoyStickLever(eventData);
        isInput = true;
    }

    public void OnDrag(PointerEventData eventData)
    {
        ControlJoyStickLever(eventData);
    }

    public void OnEndDrag(PointerEventData eventData)
    {
        lever.anchoredPosition = Vector2.zero;
        isInput = false;
        playerScript.Move(0);
        playerScript.Steer(0);
    }

    private void ControlJoyStickLever(PointerEventData eventData)
    {
        var inputPos = eventData.position - rectTransform.anchoredPosition;
        var inputVector = inputPos.magnitude < leverRange ? inputPos : inputPos.normalized * leverRange;
        lever.anchoredPosition = inputVector;

        inputDirection = inputVector / leverRange;
    }

    private void InputControlVector()
    {
        playerScript.Move(inputDirection.y);
        playerScript.Steer(inputDirection.x);
    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (isInput)
        {
            InputControlVector();
        }
    }
}
