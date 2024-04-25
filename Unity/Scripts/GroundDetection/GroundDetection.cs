using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GroundDetection : Singleton<GroundDetection>
{
    [SerializeField] GroundType DefaultGroundType = GroundType.Asphalt;
    [SerializeField] List<GroundConfig> Configs = new List<GroundConfig>();

    Dictionary<GameObject, IGroundEntity> GroundsDictionary = new Dictionary<GameObject, IGroundEntity>();

    Dictionary<GroundType, GroundConfig> ConfigsDict = new Dictionary<GroundType, GroundConfig>();
    GroundConfig DefaultGroundConfig;

    public static GroundConfig GetDefaultGroundConfig
    {
        get
        {
            if (Instance == null)
            {
                Instantiate(B.ResourcesSettings.DefaultGroundDetection);
                Debug.Log("GroundDetection has been created");
            }

            return Instance.DefaultGroundConfig;
        }
    }

    public static GroundConfig GetGroundConfig (GroundType type)
    {
        if (Instance == null)
        {
            Debug.LogError("Scene without GroundDetection");
            return new GroundConfig();
        }

        GroundConfig result;
        if (!Instance.ConfigsDict.TryGetValue(type, out result))
        {
            result = Instance.DefaultGroundConfig;
        }
        return result;
    }

    protected override void AwakeSingleton()
    {
        ConfigsDict = new Dictionary<GroundType, GroundConfig> ();
        foreach (var config in Configs)
        {
            if (!ConfigsDict.ContainsKey (config.GroundType))
            {
                ConfigsDict.Add (config.GroundType, config);
            }
            else
            {
                Debug.LogError("Has duplicate type configs");
            }
        }
        DefaultGroundConfig = GetGroundConfig(DefaultGroundType);
    }

    public static IGroundEntity GetGroundEntity(GameObject go)
    {
        if (Instance == null)
        {
            Debug.LogError("Scene witout GroundDetection");
            return null;
        }

        IGroundEntity result = null;
        if (!Instance.GroundsDictionary.TryGetValue (go, out result))
        {
            result = go.GetComponent<IGroundEntity>();
            Instance.GroundsDictionary.Add(go, result);
        }

        return result;
    }

    public enum GroundType
    {
        Default,
        Asphalt,
        Ground,
        Sand,
        Gravel,
        Dirt,
        Desert
    }
}
