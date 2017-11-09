package com.github.zametki.ajax

import com.github.openjson.JSONArray
import com.github.openjson.JSONObject
import com.github.zametki.Constants
import com.github.zametki.Context
import com.github.zametki.UserSession
import com.github.zametki.component.group.GroupTreeModel
import com.github.zametki.component.group.GroupTreeNode
import com.github.zametki.model.*
import com.github.zametki.util.ZDateFormat
import java.util.*
import java.util.stream.Collectors

object AjaxApiUtils {
    val ROOT_GROUP = Group()

    private val NOTES_LIST_DF = ZDateFormat.getInstance("dd MMMM yyyy", Constants.MOSCOW_TZ)

    fun getGroups(userId: UserId): JSONArray {
        val treeModel = GroupTreeModel.build(userId)
        val groups = JSONArray()
        treeModel.flatList().forEach { n -> groups.put(toJSON(n)) }
        return groups
    }

    private fun toJSON(node: GroupTreeNode): JSONObject? {
        val json = JSONObject()
        val groupId = node.groupId
        val g = (if (groupId.isRoot) ROOT_GROUP else Context.getGroupsDbi().getById(groupId)) ?: return null
        json.put("id", if (g.id == null) 0 else g.id!!.intValue)
        json.put("name", g.name)
        json.put("parentId", g.parentId.intValue)
        json.put("level", node.level)
        json.put("entriesCount", Context.getZametkaDbi().countByGroup(g.userId, groupId))
        val children = JSONArray()
        var i = 0
        val n = node.childCount
        while (i < n) {
            val childNode = node.getChildAt(i) as GroupTreeNode
            children.put(childNode.groupId.intValue)
            i++
        }
        json.put("children", children)
        return json
    }

    fun getGroupsListAsResponse(userId: UserId): String {
        val response = JSONObject()
        response.put("groups", getGroups(userId))
        return response.toString()
    }

    fun getNotesListAsResponse(group: Group?) = AjaxApiUtils.getNotesListAsResponse(group?.id)

    private fun getNotesListAsResponse(groupId: GroupId?): String {
        val notesArray = AjaxApiUtils.getNotes(groupId)
        return JSONObject().put("notes", notesArray).toString()
    }

    private fun getNotes(groupId: GroupId?): JSONArray {
        val noteIds = AjaxApiUtils.getList(groupId)
        val notesArray = JSONArray()
        noteIds
                .mapNotNull { Context.getZametkaDbi().getById(it) }
                .forEach { notesArray.put(toJSON(it)) }
        return notesArray
    }

    fun getNotesAndGroupsAsResponse(userId: UserId, groupId: GroupId): String {
        return JSONObject()
                .put("groups", AjaxApiUtils.getGroups(userId))
                .put("notes", AjaxApiUtils.getNotes(groupId))
                .toString()
    }

    private fun getList(groupId: GroupId?): List<ZametkaId> {
        var res = Context.getZametkaDbi().getByUser(UserSession.get().userId)
        res = if (groupId != null) {
            res.stream()
                    .map<Zametka> { id -> Context.getZametkaDbi().getById(id) }
                    .filter { z -> z != null && z.groupId == groupId }
                    .map { z -> z.id }
                    .collect(Collectors.toList<ZametkaId>())
        } else {
            ArrayList(res)
        }
        Collections.reverse(res)
        return res
    }

    private fun toJSON(z: Zametka): JSONObject {
        val json = JSONObject()
        json.put("id", z.id!!.intValue)
        json.put("group", z.groupId.intValue)
        json.put("type", z.type.id)
        json.put("content", z.content)
        json.put("dateText", NOTES_LIST_DF.format(z.creationDate))
        return json
    }

}
