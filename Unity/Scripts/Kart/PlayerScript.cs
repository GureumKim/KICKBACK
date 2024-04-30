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
    [SerializeField] private float CurrentSpeed = 0; // 스피드 적용할 변수
    [SerializeField] public float MaxSpeed; // 최대 속력
    [SerializeField] public float boostSpeed; // 부스트 스피드
    private float RealSpeed;

    [Header("Jump")]
    [Tooltip("점프 할 때 작용하는 중력")]
    [SerializeField] private float jumpForce;
    [SerializeField] private float gravity = -9.81f;
    private bool touchingGround;

    [Header("Steering & Drift")]
    public float rotateSpeed = 80f; // 캐릭터 회전 속도    
    
    // 드리프트 및 스티어링
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
        // 캐릭터의 이동 방향을 설정합니다.
        Vector3 moveDirection = Vector3.zero;

        // 입력에 따라 이동 방향을 설정합니다.
        if (Input.GetKey(KeyCode.W) || Input.GetKey(KeyCode.UpArrow))
        {
            moveDirection = transform.forward * (MaxSpeed);
            animator.SetBool("Move", true);
        }
        else if (Input.GetKey(KeyCode.S) || Input.GetKey(KeyCode.DownArrow))
        {
            moveDirection = -transform.forward * (MaxSpeed / 1.75f); // 브레이크 스피드
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

        // CharacterController의 Move 메서드를 사용하여 이동합니다.
        controller.Move(moveDirection * Time.deltaTime);
    }

    private void Steer()
    {
        // 수평 입력을 받아 steerDirection에 저장
        steerDirection = Input.GetAxis("Horizontal");

        // 스티어링에 사용할 회전 방향 벡터를 초기화
        Vector3 steerDirVect = Vector3.zero;

        // 스티어링 각도를 초기화
        float steerAmount = 0f;

        // 드리프트 상태에 따라 스티어링 각도와 회전 방향을 설정
        if (driftLeft && !driftRight)
        {
            steerAmount = steerDirection < 0 ? -1.5f : -0.5f;
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, -20f, 0f), 8f * Time.deltaTime);

            // 슬라이딩 중이고 땅에 닿아 있을 때 외부 드리프트 힘을 추가
            if (isSliding && touchingGround)
            {
                // CharacterController의 Move 함수를 통해 이동 방향으로 힘을 추가
                controller.Move(transform.right * outwardsDriftForce * Time.deltaTime);
            }
        }
        else if (driftRight && !driftLeft)
        {
            steerAmount = steerDirection > 0 ? 1.5f : 0.5f;
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, 20f, 0f), 8f * Time.deltaTime);

            // 슬라이딩 중이고 땅에 닿아 있을 때 외부 드리프트 힘을 추가
            if (isSliding && touchingGround)
            {
                // CharacterController의 Move 함수를 통해 이동 방향으로 힘을 추가
                controller.Move(transform.right * -outwardsDriftForce * Time.deltaTime);
            }
        }
        else
        {
            // 드리프트 중이 아닐 때 핸들을 원래 상태로 복귀
            transform.GetChild(0).localRotation = Quaternion.Lerp(transform.GetChild(0).localRotation, Quaternion.Euler(0f, 0f, 0f), 8f * Time.deltaTime);
        }

        // 실제 속도에 따라 스티어링 각도를 조정
        steerAmount = RealSpeed > 30f ? RealSpeed / 4f * steerDirection : RealSpeed / 1.5f * steerDirection;

        // 스티어링 각도에 따라 캐릭터를 회전
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

                // 부스터 발동 (수정 예정)
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

        // SpeedLines의 첫 번째 자식 요소를 가져옴
        Transform speedLine = SpeedLines.GetChild(0);

        if (BoostTime > 0)
        {
            // SpeedLines의 파티클 시스템이 재생 중이 아니면 재생
            if (!speedLine.GetComponent<ParticleSystem>().isPlaying)
            {
                speedLine.GetComponent<ParticleSystem>().Play();
            }

            MaxSpeed = boostSpeed;
            CurrentSpeed = Mathf.Lerp(CurrentSpeed, MaxSpeed, 1 * Time.deltaTime);
        }
        else
        {
            // SpeedLines의 파티클 시스템을 정지
            speedLine.GetComponent<ParticleSystem>().Stop();

            MaxSpeed = boostSpeed - 20;
        }
    }

    private void TestBoosts()
    {
        // SpeedLines의 첫 번째 자식 요소를 가져옴
        Transform speedLine = SpeedLines.GetChild(0);

        if (Input.GetKey(KeyCode.LeftControl) && !speedLine.GetComponent<ParticleSystem>().isPlaying)
        {
            speedLine.GetComponent<ParticleSystem>().Play();
            animator.SetBool("Run", true); // 애니메이션 실행
        }
        else if (Input.GetKey(KeyCode.LeftControl) && speedLine.GetComponent<ParticleSystem>().isPlaying)
        {
            speedLine.GetComponent<ParticleSystem>().Stop();
            animator.SetBool("Run", false); // 애니메이션 정지
        }

        // 속도 조절
        if (Input.GetKey(KeyCode.LeftControl) && CurrentSpeed < MaxSpeed)
        {
            CurrentSpeed += boostSpeed * Time.deltaTime; // 속도를 증가시킴
        }
        else
        {
            CurrentSpeed = Mathf.Max(CurrentSpeed - boostSpeed * Time.deltaTime, 0); // 속도를 감소시킴
        }
    }


    private void RotateCharacter()
    {
        float rotateAmount = 0f;

        if (Input.GetKey(KeyCode.LeftArrow) || Input.GetKey(KeyCode.A))
        {
            rotateAmount = -1f; // 왼쪽으로 회전
        }
        else if (Input.GetKey(KeyCode.RightArrow) || Input.GetKey(KeyCode.D))
        {
            rotateAmount = 1f; // 오른쪽으로 회전
        }

        if (rotateAmount != 0f)
        {
            rotateAmount *= rotateSpeed * Time.deltaTime;
            transform.Rotate(0f, rotateAmount, 0f);
        }
    }
}
