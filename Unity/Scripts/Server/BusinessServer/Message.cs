using MessagePack;

namespace Highlands.Server.BusinessServer
{
    [MessagePackObject]
    public class Message
    {
        [Key(0)]
        public Command command { get; set; }
    }
}