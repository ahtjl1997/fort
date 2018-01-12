package com.fort.webapp.controller.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fort.module.os.Os;
import com.fort.module.os.ResType;
import com.fort.module.resource.Account;
import com.fort.module.resource.Resource;
import com.fort.service.resource.ResourceService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@RequestMapping("/resource")
@Controller
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="status",defaultValue="-1") int status,
			@RequestParam(name="typeId",defaultValue="-1") int typeId,
			@RequestParam(name="osId",defaultValue="-1") int osId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		if(status != -1) {
			map.put("status", status);
		}
		if(typeId != -1) {
			map.put("typeId", typeId);
		}
		if(osId != -1) {
			map.put("osId", osId);
		}
		
		SearchResult<Resource> sr = resourceService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		model.put("paramQuery", keyword);
		model.put("typeId", typeId);
		model.put("status", status);
		model.put("osId", osId);
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		return "pages/resource/resourceList";
	}
	
	@RequestMapping("/insertPage")
	public String insertPage(@ModelAttribute("resource") Resource resource) {
		return "pages/resource/resourceInfo";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("resource") Resource resource,
			@RequestParam(name="id",defaultValue="0") int id,ModelMap model) throws JSONException {
		if(id == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		Resource defaulRes = resourceService.queryById(id);
		if(defaulRes == null) {
			throw new RuntimeException("该设备不存在或已被删除");
		}
		
		JSONArray protocolArr = new JSONArray();
		JSONObject protocol = new JSONObject();
		if(defaulRes.getUseSsh() == 1) {
			protocol.put("protocol", "SSH");
			protocol.put("port", defaulRes.getSshPort());
			protocolArr.put(protocol);
		}
		
		if(defaulRes.getUseSftp() == 1) {
			protocol = new JSONObject();
			protocol.put("protocol", "SFTP");
			protocol.put("port", defaulRes.getSftpPort());
			protocolArr.put(protocol);
		}
		
		if(defaulRes.getUseRdp() == 1) {
			protocol = new JSONObject();
			protocol.put("protocol", "RDP");
			protocol.put("port", defaulRes.getRdpPort());
			protocolArr.put(protocol);
		}
		
		JSONArray accountArr = new JSONArray();
		Collection<Account> accList = defaulRes.getAccountList();
		if(FortObjectUtil.isNotEmpty(accList)) {
			for(Account acc : accList) {
				JSONObject account = new JSONObject();
				account.put("account", acc.getName());
				account.put("password", "");
				account.put("type", acc.getType());
				accountArr.put(account);
			}
		}
		model.put("accounts", accountArr);
		model.put("protocols", protocolArr);
		resource.copy(defaulRes);
		return "pages/resource/resourceInfo";
	}
	
	@RequestMapping("/queryOs")
	@ResponseBody
	public List<Os> queryOs(@RequestParam(name="typeId",defaultValue="1")int typeId){
		return resourceService.queryOsByTypeId(typeId);
	}
	
	@RequestMapping("/queryType")
	@ResponseBody
	public List<ResType> queryType(){
		return resourceService.queryResType();
	}
	
	@RequestMapping(value="/checkName")
	@ResponseBody
	public int checkName(@RequestParam(name="resName",defaultValue="") String resName) {
		int resId = 0;
		if(GenericValidator.isBlankOrNull(resName)) {
			throw new RuntimeException("请输入设备名称");
		}
		Resource r = resourceService.queryByName(resName);
		if(FortObjectUtil.isNotEmpty(r)) {
			resId = r.getId();
		}
		return resId;
	}
	
	@RequestMapping(value="/insert")
	public String insert(@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="ip",defaultValue="") String ip,
			@RequestParam(name="typeId",defaultValue="1") int typeId,
			@RequestParam(name="osId",defaultValue="1") int osId,
			@RequestParam(name="status",defaultValue="0") int status,
			@RequestParam(name="accounts",defaultValue="[]") String accounts,
			@RequestParam(name="protocols",defaultValue="[]") String protocols) throws JSONException {
		
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入设备名称");
		}
		
		if(GenericValidator.isBlankOrNull(ip)) {
			throw new RuntimeException("请输入设备IP地址");
		}
		
		Resource temp = resourceService.queryByName(name);
		if(FortObjectUtil.isNotEmpty(temp)) {
			throw new RuntimeException("该设备名称重复，请重新填写");
		}
		
		Resource r = new Resource();
		r.setName(name);
		r.setIp(ip);
		r.setTypeId(typeId);
		r.setOsId(osId);
		r.setStatus(status);
		r.setCreateTime(new Date());
		
		JSONArray protocolArr = new JSONArray(protocols);
		if(!(protocolArr == null || protocolArr.length() == 0)) {
			int len = protocolArr.length();
			for(int index = 0;index < len; index++) {
				JSONObject json = protocolArr.getJSONObject(index);
				if("SSH".equals(json.getString("protocol"))) {
					r.setUseSsh(1);
					r.setSshPort(json.getInt("port"));
				}else if("SFTP".equals(json.getString("protocol"))) {
					r.setUseSftp(1);
					r.setSftpPort(json.getInt("port"));
				}else if("RDP".equals(json.getString("protocol"))) {
					r.setUseRdp(1);
					r.setRdpPort(json.getInt("port"));
				}
			}
		}
		
		JSONArray accountArr = new JSONArray(accounts);
		List<Account> accountList = new ArrayList<Account>();
		if(!(accountArr == null || accountArr.length() == 0)) {
			int len = accountArr.length();
			for(int index = 0;index < len; index++) {
				JSONObject json = accountArr.getJSONObject(index);
				Account acc = new Account();
				acc.setName(json.getString("account"));
				acc.setPassword(json.getString("password"));
				acc.setType(json.getInt("type"));
				if(GenericValidator.isBlankOrNull(acc.getName())) {
					continue;
				}
				if(!(acc.getType() == 0 || acc.getType() == 1)){
					continue;
				}
				accountList.add(acc);
			}
		}

		r.setAccountList(accountList);
		resourceService.insert(r);
		
		return "redirect:/resource/query";
	}
	
	@RequestMapping(value="/update")
	public String update(@RequestParam(name="id",defaultValue="0") int id,
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="ip",defaultValue="") String ip,
			@RequestParam(name="typeId",defaultValue="1") int typeId,
			@RequestParam(name="osId",defaultValue="1") int osId,
			@RequestParam(name="status",defaultValue="0") int status,
			@RequestParam(name="accounts",defaultValue="[]") String accounts,
			@RequestParam(name="protocols",defaultValue="[]") String protocols) throws JSONException {
		if(id == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		
		if(GenericValidator.isBlankOrNull(ip)) {
			throw new RuntimeException("请输入设备IP地址");
		}
		
		Resource r = resourceService.queryById(id);
		if(r == null) {
			throw new RuntimeException("该设备不存在或已被删除");
		}
		
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入设备名称");
		}
		
		Resource tmp = resourceService.queryByName(name);
		if(!(FortObjectUtil.isEmpty(tmp) || tmp.getId() == id)) {
			throw new RuntimeException("该设备名称重复，请重新填写");
		}
		
		r.setName(name);
		r.setIp(ip);
		r.setTypeId(typeId);
		r.setOsId(osId);
		r.setStatus(status);
		r.setUpdateTime(new Date());
		
		r.setUseSsh(0);
		r.setSshPort(22);
		r.setUseSftp(0);
		r.setSftpPort(22);
		r.setUseRdp(0);
		r.setRdpPort(3389);
		
		JSONArray protocolArr = new JSONArray(protocols);
		if(!(protocolArr == null || protocolArr.length() == 0)) {
			int len = protocolArr.length();
			for(int index = 0;index < len; index++) {
				JSONObject json = protocolArr.getJSONObject(index);
				if("SSH".equals(json.getString("protocol"))) {
					r.setUseSsh(1);
					r.setSshPort(json.getInt("port"));
				}else if("SFTP".equals(json.getString("protocol"))) {
					r.setUseSftp(1);
					r.setSftpPort(json.getInt("port"));
				}else if("RDP".equals(json.getString("protocol"))) {
					r.setUseRdp(1);
					r.setRdpPort(json.getInt("port"));
				}
			}
		}
		
		JSONArray accountArr = new JSONArray(accounts);
		List<Account> accountList = new ArrayList<Account>();
		if(!(accountArr == null || accountArr.length() == 0)) {
			int len = accountArr.length();
			for(int index = 0;index < len; index++) {
				JSONObject json = accountArr.getJSONObject(index);
				Account acc = new Account();
				acc.setName(json.getString("account"));
				acc.setPassword(json.getString("password"));
				acc.setType(json.getInt("type"));
				if(GenericValidator.isBlankOrNull(acc.getName())) {
					continue;
				}
				if(!(acc.getType() == 0 || acc.getType() == 1)){
					continue;
				}
				accountList.add(acc);
			}
		}

		r.setAccountList(accountList);
		
		resourceService.update(r);
		
		return "redirect:/resource/query";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(name="id",defaultValue = "0") int id) {
		if(id == 0) {
			throw new RuntimeException("无效的ID信息");
		}
		Resource r = resourceService.queryById(id);
		if(r == null) {
			throw new RuntimeException("该设备不存在或已被删除");
		}
		resourceService.delete(id);
		return "redirect:/resource/query";
	}
	
	@RequestMapping("/deletes")
	public String deletes(@RequestParam(name="ids",defaultValue = "") String ids) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的设备ID");
		}
		String[] idArr = {};
		if(ids.indexOf(',') == -1) {
			idArr = new String[] {ids};
		}else {
			idArr = ids.split(",");
		}
		String idStr = "";
		int[] int_arr = new int[idArr.length];
		for(int index = 0; index < idArr.length; index++) {
			idStr = idArr[index];
			if(GenericValidator.isInt(idStr)){
				int_arr[index] = Integer.valueOf(idStr);
			}
		}
		resourceService.delete(int_arr);
		return "redirect:/resource/query";
	}
	
	@RequestMapping(value="/status")
	public String status(@RequestParam(name="id",defaultValue="0") int id,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(id == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		Resource r = resourceService.queryById(id);
		if(FortObjectUtil.isEmpty(r)) {
			throw new RuntimeException("该设备不存在或已被删除");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("无效的状态信息");
		}
		resourceService.updateStatus(id, status);
		return "redirect:/resource/query";
	}
	
	@RequestMapping(value="/statuss")
	public String statuss(@RequestParam(name="ids",defaultValue="0") String ids,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的设备ID");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("无效的状态信息");
		}
		String[] idArr = {};
		if(ids.indexOf(',') == -1) {
			idArr = new String[] {ids};
		}else {
			idArr = ids.split(",");
		}
		String idStr = "";
		int[] int_arr = new int[idArr.length];
		for(int index = 0; index < idArr.length; index++) {
			idStr = idArr[index];
			if(GenericValidator.isInt(idStr)){
				int_arr[index] = Integer.valueOf(idStr);
			}
		}
		resourceService.updateStatus(int_arr, status);
		return "redirect:/resource/query";
	}
}
