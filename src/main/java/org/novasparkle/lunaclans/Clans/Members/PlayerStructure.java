package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlayerStructure {
    @Setter
    private Member leader;
    private List<Member> members = new ArrayList<>();
    private final int membersCount;

    public PlayerStructure(OfflinePlayer leader) {
        this.leader = new Member(leader.getName(), StatusManager.getLeader());
        this.members.add(this.leader);
        this.membersCount = 1;
    }
    public PlayerStructure(Member leader, List<Member> members) {
        this.leader = leader;
        this.members = members;
        this.members.add(this.leader);
        this.membersCount = members.size();
    }
    public boolean containsMember(OfflinePlayer player) {
        if (this.members.isEmpty()) return false;
        return this.getMembers().stream().anyMatch(m -> m.getPlayer().getUniqueId().equals(player.getUniqueId()));
    }
    public void addMember(String nick, Status status) {
        this.members.add(new Member(nick, status));
    }
    public void changeLeader(Member newLeader) {
        newLeader.setStatus(StatusManager.getLeader());
        leader.setStatus(StatusManager.getByTag(ConfigManager.getString("clan.changeLeaderStatusTag")));
        this.setLeader(newLeader);
    }
    public List<Member> getMembersByStatus(Status status) {
        return this.members.stream().filter(m -> m.getStatus().equals(status)).collect(Collectors.toList());
    }
    public Status getMemberStatus(String nick) {
        return this.members.stream().filter(p -> p.getName().equals(nick)).map(Member::getStatus).findFirst().orElse(null);
    }
    public Status getMemberStatus(Member member) {
        return this.members.stream().filter(p -> p.equals(member)).map(Member::getStatus).findFirst().orElse(null);
    }

    public void promote(Member member, Member invoker) {
        Status status = member.getStatus();
        if (invoker.getStatus().isLower(status)) {
            invoker.sendMessage(MsgManager.getMessage("lowerStatusPromote")
                    .replace("[member]", member.getName())
                    .replace("[status]", status.next().getPrefix()));
        } else if (invoker.getStatus().equals(status)) {
            invoker.sendMessage(MsgManager.getMessage("equalStatusPromote")
                    .replace("[player]", member.getName()));

        } else {
            Status newStatus = status.next();
            assert newStatus != null;
            if (newStatus.equals(invoker.getStatus())) {
                invoker.sendMessage(MsgManager.getMessage("nextStatusIsEquals"));
                return;
            }
            member.setStatus(newStatus);
            invoker.sendMessage(MsgManager.getMessage("promoted")
                    .replace("[member]", member.getName())
                    .replace("[status]", newStatus.getPrefix()));
        }
    }

    public void demote(Member member, Member invoker) {
        Status status = member.getStatus();
        if (invoker.getStatus().isLower(status)) {
            invoker.sendMessage(MsgManager.getMessage("lowerStatusDemote")
                    .replace("[member]", member.getName()));

        } else if (invoker.getStatus().equals(status)) {
            invoker.sendMessage(MsgManager.getMessage("equalStatusDemote")
                    .replace("[player]", member.getName()));

        } else {
            Status newStatus = status.previous();
            if (newStatus == null) {
                invoker.sendMessage(MsgManager.getMessage("minStatus").replace("[player]", member.getName()));
                return;
            }
            member.setStatus(newStatus);
            invoker.sendMessage(MsgManager.getMessage("demoted")
                    .replace("[member]", member.getName())
                    .replace("[status]", newStatus.getPrefix()));
        }
    }
}
