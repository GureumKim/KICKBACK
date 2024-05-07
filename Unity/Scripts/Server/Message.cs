using MessagePack;

namespace Highlands.Server
{
    [MessagePackObject]
    public class Message
    {
        [Key(0)]
        public virtual int command { get; set; }
        [Key(1)]
        public virtual int channelIndex { get; set; }
        [Key(2)]
        public virtual string userName { get; set; }
        [Key(3)]
        public virtual string message { get; set; }
    }
}