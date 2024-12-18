package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
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

    public PlayerStructure(Player leader) {
        this.leader = new Member(leader.getName(), Status.LEADER);
        this.members.add(this.leader);
        this.membersCount = 1;
    }
    public PlayerStructure(Member leader, List<Member> members) {
        this.leader = leader;
        this.members = members;
        this.members.add(this.leader);
        this.membersCount = members.size();
    }
    public boolean containsMember(Player player) {
        if (this.members.isEmpty()) return false;
        return this.getMembers().stream().anyMatch(m -> m.getPlayer().getUniqueId().equals(player.getUniqueId()));
    }

    public void changeLeader(Member newLeader) {
        newLeader.setStatus(Status.LEADER);
        leader.setStatus(Status.DEPUTY_LEADER);
        this.setLeader(newLeader);
    }
    public List<Member> getMembersByStatus(Status status) {
        return this.members.stream().filter(m -> m.getStatus().equals(status)).collect(Collectors.toList());
    }
    public Status getMemberStatus(String player) {
        return this.members.stream().filter(p -> p.getNickName().equals(player)).map(Member::getStatus).findFirst().orElse(null);
    }
    public Status getMemberStatus(Member member) {
        return this.members.stream().filter(p -> p.equals(member)).map(Member::getStatus).findFirst().orElse(null);
    }

    public void promote(Member member, Member invoker) {
        Status status = member.getStatus();
        if (invoker.getStatus().isLower(status)) {
            invoker.sendMessage(MsgManager.getMessage("lowerStatusPromote")
                    .replace("[member]", member.getNickName())
                    .replace("[status]", status.next().getPrefix()));
        } else if (invoker.getStatus().equals(status)) {
            invoker.sendMessage(MsgManager.getMessage("equalStatusPromote")
                    .replace("[player]", member.getNickName()));

        } else {
            Status newStatus = status.next();
            member.setStatus(newStatus);

            assert newStatus != null;
            invoker.sendMessage(MsgManager.getMessage("demoted")
                    .replace("[member]", member.getNickName())
                    .replace("[status]", newStatus.getPrefix()));
        }
    }

    public void demote(Member member, Member invoker) {
        Status status = member.getStatus();
        if (invoker.getStatus().isLower(status)) {
            invoker.sendMessage(MsgManager.getMessage("lowerStatusDemote")
                    .replace("[member]", member.getNickName()));

        } else if (invoker.getStatus().equals(status)) {
            invoker.sendMessage(MsgManager.getMessage("equalStatusDemote")
                    .replace("[player]", member.getNickName()));

        } else {
            Status newStatus = status.previous();
            if (newStatus == null) {
                invoker.sendMessage(MsgManager.getMessage("minStatus").replace("[player]", member.getNickName()));
                return;
            }
            member.setStatus(newStatus);
            invoker.sendMessage(MsgManager.getMessage("demoted")
                    .replace("[member]", member.getNickName())
                    .replace("[status]", newStatus.getPrefix()));
        }
    }
}
