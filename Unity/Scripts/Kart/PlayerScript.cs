using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.UI;

public class PlayerScript : MonoBehaviour
{
    private CharacterController controller;
    private Animator animator;

    [Header("Movement")]
    [SerializeField] private float CurrentSpeed = 0; // ���ǵ� ������ ����
    [SerializeField] public float MaxSpeed; // �ִ� �ӷ�
    [SerializeField] public float boostSpeed; // �ν�Ʈ ���ǵ�
    private float RealSpeed;

    [Header("Jump")]
    [Tooltip("���� �� �� �ۿ��ϴ� �߷�")]
    [SerializeField] private float jumpForce;
    [SerializeField] private float gravity = -9.81f;
    private bool touchingGround;

    [Header("Steering & Drift")]
    public float rotateSpeed = 80f; // ĳ���� ȸ�� �ӵ�    
    
    // �帮��Ʈ �� ��Ƽ�
    private float steerDirection;
    private float driftTime;

    bool driftLeft = false;
    bool driftRight = false;
    float outwardsDriftForce = 50000;



    public bool isSliding = false;

    [Header("Particles Drift Sparks")]
    public Transform leftDrift;
    public Transform rightDrift;
    public Color drift1;
    public Color drift2;
    public Color drift3;

    [HideInInspector]
    public float BoostTime = 0;

    public Transform SpeedLines;

    void Start()
    {
        controller = GetComponent<CharacterController>();
        animator = GetComponent<Animator>();
    }

    void FixedUpdate()
    {
        Move();
        Steer();
        GroundNormalRotiation();
        Drift();
        Boosts();
        RotateCharacter();

        TestBoosts();
    }

    private void Move()
    {
        // ĳ������ �̵� ������ �����մϴ�.
        Vector3 moveDirection = Vector3.zero;

        // �Է¿� ���� �̵� ������ �����մϴ�.
        if (Input.GetKey(KeyCode.W) || Input.GetKey(KeyCode.UpArrow))
        {
            moveDirection = transform.forward * (MaxSpeed);
            animator.SetBool("Move", true);
        }
        else if (Input.GetKey(KeyCode.S) || Input.GetKey(KeyCode.DownArrow))
        {
            moveDirection = -transform.forward * (MaxSpeed / 1.75f); // �극��ũ ���ǵ�
            animator.SetBool("Move", true);
        }
        else
        {
            moveDirection = Vector3.zero;
            animator.SetBool("Move", false);
        }

        if (Input.GetKey(KeyCode.Space) && touchingGround)
        {
            moveDirection.y = Mathf.Sqrt(jumpForce * -2f * gravity);
            animator.SetTrigger("Jump");
        }

        // CharacterController�� Move �޼��带 ����Ͽ� �̵��մϴ�.
        controller.Move(moveDirection * Time.deltaTime);
    }

    private void Steer()
    {
        // ���� �Է��� �޾� steerDirection�� ����
        steerDirection = Input.GetAxis("Horizontal");

        // ��Ƽ��� ����� ȸ�� ���� ���͸� �ʱ�ȭ
        Vector3 steerDirVect = Vector3.zero;

        // ��Ƽ� ������ �ʱ�ȭ
        float steerAmount = 0f;

        // �帮��Ʈ ���¿� ���� ��Ƽ� ������ ȸ�� ������ ����
        if (driftLeft && !driftRight)
        {
            steerAmount = steerDirection < 0 ? -1.5f : -0.5f;
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, -20f, 0f), 8f * Time.deltaTime);

            // �����̵� ���̰� ���� ��� ���� �� �ܺ� �帮��Ʈ ���� �߰�
            if (isSliding && touchingGround)
            {
                // CharacterController�� Move �Լ��� ���� �̵� �������� ���� �߰�
                controller.Move(transform.right * outwardsDriftForce * Time.deltaTime);
            }
        }
        else if (driftRight && !driftLeft)
        {
            steerAmount = steerDirection > 0 ? 1.5f : 0.5f;
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, 20f, 0f), 8f * Time.deltaTime);

            // �����̵� ���̰� ���� ��� ���� �� �ܺ� �帮��Ʈ ���� �߰�
            if (isSliding && touchingGround)
            {
                // CharacterController�� Move �Լ��� ���� �̵� �������� ���� �߰�
                controller.Move(transform.right * -outwardsDriftForce * Time.deltaTime);
            }
        }
        else
        {
            // �帮��Ʈ ���� �ƴ� �� �ڵ��� ���� ���·� ����
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, 0f, 0f), 8f * Time.deltaTime);
        }

        // ���� �ӵ��� ���� ��Ƽ� ������ ����
        steerAmount = RealSpeed > 30f ? RealSpeed / 4f * steerDirection : RealSpeed / 1.5f * steerDirection;

        // ��Ƽ� ������ ���� ĳ���͸� ȸ��
        steerDirVect = new Vector3(transform.eulerAngles.x, transform.eulerAngles.y + steerAmount, transform.eulerAngles.z);
        transform.eulerAngles = Vector3.Lerp(transform.eulerAngles, steerDirVect, 3f * Time.deltaTime);
    }


    private void GroundNormalRotiation()
    {
        RaycastHit hit;
        if (Physics.Raycast(transform.position, -transform.up, out hit, 0.75f))
        {
            transform.rotation = Quaternion.Lerp(transform.rotation, Quaternion.FromToRotation(transform.up * 2, hit.normal) * transform.rotation, 7.5f * Time.deltaTime);
            touchingGround = true;
        }
        else
        {
            touchingGround = false;
        }
    }

    private void Drift()
    {
        if (Input.GetKeyDown(KeyCode.LeftShift) && touchingGround)
        {
            animator.SetTrigger("Hop");
            
            if (steerDirection > 0)
            {
                driftRight = true;
                driftLeft = false;
            }
            else if (steerDirection < 0)
            {
                driftRight = false;
                driftLeft = true;
            }
        }

        if (Input.GetKeyDown(KeyCode.LeftShift) && touchingGround && CurrentSpeed > 40 && Input.GetAxis("Horizontal") != 0)
        {
            driftTime += Time.deltaTime;

            // particle effects (sparks)
            if (driftTime >= 1.5 && driftTime < 4)
            {
                for (int i = 0; i < leftDrift.childCount; i++)
                {
                    ParticleSystem DriftPS = rightDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>(); // right wheel particles
                    ParticleSystem.MainModule PSMAIN = DriftPS.main;

                    ParticleSystem DriftPS2 = leftDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>(); // left wheel particles
                    ParticleSystem.MainModule PSMAIN2 = DriftPS2.main;

                    PSMAIN.startColor = drift1;
                    PSMAIN2.startColor = drift1;

                    if (!DriftPS.isPlaying && !DriftPS2.isPlaying)
                    {
                        DriftPS.Play();
                        DriftPS2.Play();
                    }
                }
            }
            if (driftTime >= 4 && driftTime < 7)
            {
                // drift color particles
                for (int i = 0; i < leftDrift.childCount; i++) 
                { 
                    ParticleSystem DriftPS = rightDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>();
                    ParticleSystem.MainModule PSMAIN = DriftPS.main;

                    ParticleSystem DriftPS2 = leftDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>();
                    ParticleSystem.MainModule PSMAIN2 = DriftPS.main;

                    PSMAIN.startColor = drift2;
                    PSMAIN2.startColor= drift2;
                }
            }
            if (driftTime >= 7)
            {
                for (int i = 0; i < leftDrift.childCount; i++)
                {

                    ParticleSystem DriftPS = rightDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>();
                    ParticleSystem.MainModule PSMAIN = DriftPS.main;

                    ParticleSystem DriftPS2 = leftDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>();
                    ParticleSystem.MainModule PSMAIN2 = DriftPS2.main;

                    PSMAIN.startColor = drift3;
                    PSMAIN2.startColor = drift3;

                }
            }

            if (!Input.GetKey(KeyCode.LeftShift) || RealSpeed < 40)
            {
                driftLeft = false;
                driftRight = false;
                isSliding = false;

                // �ν��� �ߵ� (���� ����)
                if (driftTime > 1.5 && driftTime < 4)
                {
                    BoostTime = 0.75f;
                }
                if (driftTime >= 4 && driftTime < 7)
                {
                    BoostTime = 1.5f;

                }
                if (driftTime >= 7)
                {
                    BoostTime = 2.5f;

                }

                //reset everything
                driftTime = 0;
                //stop particles
                for (int i = 0; i < 5; i++)
                {
                    ParticleSystem DriftPS = rightDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>(); //right wheel particles
                    ParticleSystem.MainModule PSMAIN = DriftPS.main;

                    ParticleSystem DriftPS2 = leftDrift.transform.GetChild(i).gameObject.GetComponent<ParticleSystem>(); //left wheel particles
                    ParticleSystem.MainModule PSMAIN2 = DriftPS2.main;

                    DriftPS.Stop();
                    DriftPS2.Stop();

                }
            }
        }
    }

    private void Boosts()
    {
        BoostTime -= Time.deltaTime;

        // SpeedLines�� ù ��° �ڽ� ��Ҹ� ������
        Transform speedLine = SpeedLines.GetChild(0);

        if (BoostTime > 0)
        {
            // SpeedLines�� ��ƼŬ �ý����� ��� ���� �ƴϸ� ���
            if (!speedLine.GetComponent<ParticleSystem>().isPlaying)
            {
                speedLine.GetComponent<ParticleSystem>().Play();
            }

            MaxSpeed = boostSpeed;
            CurrentSpeed = Mathf.Lerp(CurrentSpeed, MaxSpeed, 1 * Time.deltaTime);
        }
        else
        {
            // SpeedLines�� ��ƼŬ �ý����� ����
            speedLine.GetComponent<ParticleSystem>().Stop();

            MaxSpeed = boostSpeed - 20;
        }
    }

    private void TestBoosts()
    {
        // SpeedLines�� ù ��° �ڽ� ��Ҹ� ������
        Transform speedLine = SpeedLines.GetChild(0);

        if (Input.GetKey(KeyCode.LeftControl) && !speedLine.GetComponent<ParticleSystem>().isPlaying)
        {
            speedLine.GetComponent<ParticleSystem>().Play();
            animator.SetBool("Run", true); // �ִϸ��̼� ����
        }
        else if (Input.GetKey(KeyCode.LeftControl) && speedLine.GetComponent<ParticleSystem>().isPlaying)
        {
            speedLine.GetComponent<ParticleSystem>().Stop();
            animator.SetBool("Run", false); // �ִϸ��̼� ����
        }

        // �ӵ� ����
        if (Input.GetKey(KeyCode.LeftControl) && CurrentSpeed < MaxSpeed)
        {
            CurrentSpeed += boostSpeed * Time.deltaTime; // �ӵ��� ������Ŵ
        }
        else
        {
            CurrentSpeed = Mathf.Max(CurrentSpeed - boostSpeed * Time.deltaTime, 0); // �ӵ��� ���ҽ�Ŵ
        }
    }


    private void RotateCharacter()
    {
        float rotateAmount = 0f;

        if (Input.GetKey(KeyCode.LeftArrow) || Input.GetKey(KeyCode.A))
        {
            rotateAmount = -1f; // �������� ȸ��
        }
        else if (Input.GetKey(KeyCode.RightArrow) || Input.GetKey(KeyCode.D))
        {
            rotateAmount = 1f; // ���������� ȸ��
        }

        if (rotateAmount != 0f)
        {
            rotateAmount *= rotateSpeed * Time.deltaTime;
            transform.Rotate(0f, rotateAmount, 0f);
        }
    }
}
