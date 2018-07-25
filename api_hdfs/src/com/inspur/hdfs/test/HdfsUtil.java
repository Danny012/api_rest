package com.inspur.hdfs.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivilegedExceptionAction;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

import com.inspur.hdfs.util.logException;
import com.sun.jersey.server.impl.model.method.dispatch.VoidVoidDispatchProvider;

public class HdfsUtil {
	static Logger logger = Logger.getLogger(HdfsUtil.class);
	static logException le = new logException();
	 public static void main(String[] args) throws Exception {
	
	 try {
	changeDirAllRight("/cuiaq", 0, 0, 0);
	 System.out.println("ok");
	 } catch (Exception e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	public final static Map PERMISSIONMAP = new HashMap();
	 static {
	 PERMISSIONMAP.put(7, FsAction.ALL);
	 PERMISSIONMAP.put(6, FsAction.READ_WRITE);
	 PERMISSIONMAP.put(5, FsAction.READ_EXECUTE);
	 PERMISSIONMAP.put(4, FsAction.READ);
	 PERMISSIONMAP.put(3, FsAction.WRITE_EXECUTE);
	 PERMISSIONMAP.put(2, FsAction.WRITE);
	 PERMISSIONMAP.put(1, FsAction.EXECUTE);
	 PERMISSIONMAP.put(0, FsAction.NONE);
	 }

	public static FileSystem getFileSystem() throws Exception {
		FileSystem fs = null;
		// String principal = "cuiaq1218-tenant@IDAP.COM";// user为用户
		String principal = "hdfs-cluster_indata@INDATA.COM";
		final Configuration myconf2 = new Configuration();
		// 初始化配置文件
		myconf2.set("fs.defaultFS", "hdfs://prod");
		myconf2.set("dfs.nameservices", "prod");
		myconf2.set("dfs.ha.namenodes.prod", "nn1,nn2");
		myconf2.set("dfs.namenode.rpc-address.prod.nn1", "indata-10-110-13-164.indata.com:8020");
		myconf2.set("dfs.namenode.rpc-address.prod.nn2", "indata-10-110-13-165.indata.com:8020");
		myconf2.set("dfs.client.failover.proxy.provider.prod",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		myconf2.set("hadoop.security.authentication", "kerberos");
		myconf2.set("hadoop.security.authorization", "true");
		// 找到集群的管理员用户
		myconf2.set("dfs.cluster.administrators", "hdfs");
		myconf2.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

		System.clearProperty("java.security.krb5.conf");
		// 获取krb5.conf文件

		File f1 = new File("resource/krb5.conf");
		File f2 = new File("resource/hdfs.headless.keytab");
		String krbStr = f1.getAbsolutePath();
		String userkeytab = f2.getAbsolutePath();
		// String krbStr =
		// Thread.currentThread().getContextClassLoader().getResource("krb5.conf").getFile();
		// String userkeytab =
		// Thread.currentThread().getContextClassLoader().getResource("hdfs.headless.keytab")
		// .getFile();
		// 初始化配置文件
		System.setProperty("java.security.krb5.conf", krbStr);
		// 使用票据和凭证进行认证
		UserGroupInformation.setConfiguration(myconf2);
		UserGroupInformation.loginUserFromKeytab(principal, userkeytab);
		fs = FileSystem.get(myconf2);
		return fs;
	}

	public static List<Map> listFileStatuses(String path) throws Exception {
		List resList = new ArrayList();
		FileSystem fs = getFileSystem();

		if (null == path || "".equals(path)) {
			return null;
		} else {
			FileStatus[] statuses;
			statuses = fs.listStatus(new Path(path));
			for (FileStatus fileStatus : statuses) {
				Map map = new HashMap();
				map.put("fileName", fileStatus.getPath().getName());
				// map.put("permission", fileStatus.getPermission().toString());
				// map.put("owner", fileStatus.getOwner());
				// map.put("group", fileStatus.getGroup());
				// map.put("length", fileStatus.getLen());
				// map.put("modificationTime", format(fileStatus.getModificationTime()));
				// map.put("path", fileStatus.getPath().toString());
				// map.put("isDirectory", fileStatus.isDirectory());
				resList.add(map);
			}
		}
		return resList;

	}

	public static String obtainPermission(String path) throws Exception {
		String str = null;
		Path hdfsDir = new Path(path);
		FileSystem fs = getFileSystem();
		try {
			FileStatus fileStatus = fs.getFileStatus(new Path(path));
			str = fileStatus.getPermission().toString();
		} catch (IOException e) {
			logger.error(le.getTrace(e));
			e.printStackTrace();
		}
		return str;
	}

	private static String format(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Timestamp(time));
	}

	/**
	 * 在指定集群Hdfs上创建目录
	 * 
	 * @param clusterId
	 *            集群实例ID
	 * @param dir
	 *            hdfs文件目录
	 * @return boolean 是否创建成功
	 * @throws Exception
	 */
	public static boolean makeDir(String dir) throws Exception {
		boolean isSuccess = false;
		Path hdfsDir = new Path(dir);
		FileSystem fs = getFileSystem();
		isSuccess = fs.mkdirs(hdfsDir);
		return isSuccess;
	}

	/**
	 * 在指定集群Hdfs上创建目录,权限为777
	 * 
	 * @param clusterId
	 *            集群实例ID
	 * @param dir
	 *            hdfs文件目录
	 * @return boolean 是否创建成功
	 * @throws Exception
	 */
	public static boolean makeDirAllRight(String dir) throws Exception {
		boolean isSuccess = false;
		Path hdfsDir = new Path(dir);
		FileSystem fs = getFileSystem();

		FsPermission permission = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
		isSuccess = fs.mkdirs(hdfsDir);
		if (isSuccess)
			fs.setPermission(hdfsDir, permission);

		return isSuccess;
	}

	/**
	 * 在指定集群中创建文件
	 * 
	 * @param clusterId
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public static boolean createFile(String dir) throws Exception {
		boolean isSuccess = false;
		Path hdfsDir = new Path(dir);
		FileSystem fs = getFileSystem();

		isSuccess = fs.createNewFile(hdfsDir);

		return isSuccess;
	}

	public static boolean isDirectory(String path) throws Exception {

		FileSystem fs = getFileSystem();
		return fs.isDirectory(new Path(path));

	}

	public static boolean canBeDeleted(String[] filePathArr) throws Exception {
		FileSystem fs = getFileSystem();
		Map resMap = new HashMap();
		boolean res = true;
		String message = "";

		for (String filePath : filePathArr) {
			if (fs.exists(new Path(filePath))) {
				if (fs.isDirectory(new Path(filePath))) {
					FileStatus[] arr = fs.listStatus(new Path(filePath));
					if (arr.length > 0) {
						res = false;
						message = "文件夹" + new Path(filePath).getName() + "不为空，不能被删除!";
						break;
					}
				}
			}
		}

		resMap.put("result", res);
		resMap.put("message", message);
		// System.out.println(resMap);
		// return resMap;
		return res;
	}

	/**
	 * 以输入流的方式上传文件到指定集群Hdfs目录下(上传的文件被命名为后面的path)
	 * 
	 * @param clusterId
	 *            集群实例ID
	 * @param fis
	 *            输入流
	 * @param path
	 *            hdfs文件路径
	 * @return boolean 是否上传成功
	 * @throws Exception
	 */

	public static boolean uploadFile(String localPath, String path) throws Exception {
		FileSystem fs = getFileSystem();

		fs.copyFromLocalFile(new Path(localPath), new Path(path));
		return true;

	}

	/*
	 * //** 以输入流的方式从指定集群Hdfs上获取文件信息
	 * 
	 * @param clusterId 集群实例ID
	 * 
	 * @param path hdfs文件路径
	 * 
	 * @return Map 文件内容
	 * 
	 * @throws IOException
	 */
	public static Map readFile(String path) throws Exception {
		Map res = new HashMap();
		StringBuffer sb = new StringBuffer();
		FileSystem filesystem = getFileSystem();
		FSDataInputStream fs = null;
		BufferedReader bis = null;

		fs = filesystem.open(new Path(path));
		bis = new BufferedReader(new InputStreamReader(fs, "UTF-8"));
		if (path != null) {
			String temp;
			while ((temp = bis.readLine()) != null) {
				sb.append(temp);
				sb.append("<br>");
			}
			String str = sb.toString();
			str = str.replaceAll("\\\\N", "null"); // 处理空值
			str = str.replaceAll("\001", ","); // 处理特殊字符
			res.put("code", "1");// 获取成功
			res.put("msg", "获取成功");
			res.put("value", str);
		} else {
			res.put("code", "0");// 获取失败
			res.put("msg", "数据路径为空");
			res.put("value", "");
		}

		if (fs != null) {
			bis.close();
			fs.close();
		}
		return res;
	}

	public static String deleteFile(String filePath) throws Exception {
		String message = "";
		FileSystem fs = getFileSystem();
		boolean temp = false;

		if (fs.exists(new Path(filePath))) {
			temp = fs.delete(new Path(filePath), false);
			if (!temp)
				message = message + "文件" + new Path(filePath).getName() + "删除失败!";
		}

		return message;
		// return temp;
	}

	public static boolean deleteFile2(String filePath) throws Exception {
		FileSystem fs = getFileSystem();
		boolean flag = false;

		if (fs.exists(new Path(filePath)))
			flag = fs.delete(new Path(filePath), false);

		return flag;
	}

	/**
	 * 判断某个路径是否已经存在
	 * 
	 * @param clusterId
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean existPath(String path) throws Exception {
		FileSystem fs = getFileSystem();

		return fs.exists(new Path(path));

	}

	/**
	 * 重命名文件
	 * 
	 * @param clusterId
	 * @param path1
	 * @param path2
	 * @return
	 * @throws Exception
	 */
	public static boolean rename(String path1, String path2) throws Exception {
		FileSystem fs = getFileSystem();

		return fs.rename(new Path(path1), new Path(path2));

	}

	public static boolean changeDirAllRight(String dir, int str_user, int str_group, int str_other) throws Exception {
		boolean isSuccess = false;
		Path hdfsDir = new Path(dir);
		FileSystem fs = getFileSystem();
 
		FsPermission permission = new FsPermission((FsAction) PERMISSIONMAP.get(str_user),
				(FsAction) PERMISSIONMAP.get(str_group), 
				(FsAction) PERMISSIONMAP.get(str_other)			
				);
		isSuccess = fs.mkdirs(hdfsDir);
	if (isSuccess) {
			fs.setPermission(hdfsDir, permission);
		}

		return isSuccess;
	}

	public static boolean changeFileAllRight(String dir, int str_user, int str_group, int str_other)
			// TODO Auto-generated method stub
			throws Exception {
		boolean isSuccess = true;
		Path hdfsDir = new Path(dir);
		FileSystem fs = getFileSystem();

		FsPermission permission = new FsPermission((FsAction) PERMISSIONMAP.get(str_user),
				(FsAction) PERMISSIONMAP.get(str_group), (FsAction) PERMISSIONMAP.get(str_other));
		fs.setPermission(hdfsDir, permission);

		return isSuccess;
	}

	public static Map getFileStatusWithQuota(String path) throws Exception {
		Map map = new HashMap();
		FileSystem fs = getFileSystem();

		if (fs.exists(new Path(path))) {
			FileStatus fileStatus = fs.getFileStatus(new Path(path));
			if (fileStatus.isDirectory()) {
				ContentSummary c = fs.getContentSummary(fileStatus.getPath());
				long spaceQuota = c.getSpaceQuota();
				long numQuota = c.getQuota();
				String quota = "";
				if (spaceQuota <= 0) {
					if (numQuota <= 0)
						quota = "--, --";
					else
						quota = "--, " + numQuota;
				} else {
					if (numQuota <= 0) {
						int GB = (int) (spaceQuota >> 30);
						if (GB % 1024 == 0) {
							spaceQuota = GB >> 10;
							quota = spaceQuota + "TB, --";
						} else {
							spaceQuota = GB;
							quota = spaceQuota + "GB, --";
						}
					} else {
						int GB = (int) (spaceQuota >> 30);
						if (GB % 1024 == 0) {
							spaceQuota = GB >> 10;
							quota = spaceQuota + "TB, " + numQuota;
						} else {
							spaceQuota = GB;
							quota = spaceQuota + "GB, " + numQuota;
						}
					}
				}
				map.put("quota", quota);
				map.put("length", c.getLength());
			} else {
				map.put("quota", "--, --");
				map.put("length", fileStatus.getLen());
			}
			map.put("fileName", path);
			map.put("owner", fileStatus.getOwner());
			map.put("group", fileStatus.getGroup());
			map.put("modificationTime", format(fileStatus.getModificationTime()));
			map.put("path", path);
			map.put("isDirectory", fileStatus.isDirectory());
		} else
			return null;

		return map;
	}

	public static List<Map> listFileStatusesWithQuota(String path) throws Exception {
		List resList = new ArrayList();
		FileSystem fs = getFileSystem();

		if (null == path || "".equals(path))
			return null;
		FileStatus[] statuses;
		statuses = fs.listStatus(new Path(path));
		for (FileStatus fileStatus : statuses) {
			Map map = new HashMap();
			map.put("fileName", fileStatus.getPath().getName());
			map.put("permission", fileStatus.getPermission().toString());
			map.put("owner", fileStatus.getOwner());
			map.put("group", fileStatus.getGroup());
			map.put("length", fileStatus.getLen());
			map.put("modificationTime", format(fileStatus.getModificationTime()));
			map.put("path", fileStatus.getPath().toString());
			map.put("isDirectory", fileStatus.isDirectory());
			if (fileStatus.isDirectory()) {
				ContentSummary c = fs.getContentSummary(fileStatus.getPath());
				long spaceQuota = c.getSpaceQuota();
				long numQuota = c.getQuota();
				String quota = "";
				if (spaceQuota <= 0) {
					if (numQuota <= 0)
						quota = "--, --";
					else
						quota = "--, " + numQuota;
				} else {
					if (numQuota <= 0) {
						int GB = (int) (spaceQuota >> 30);
						if (GB % 1024 == 0) {
							spaceQuota = GB >> 10;
							quota = spaceQuota + "TB, --";
						} else {
							spaceQuota = GB;
							quota = spaceQuota + "GB, --";
						}
					} else {
						int GB = (int) (spaceQuota >> 30);
						if (GB % 1024 == 0) {
							spaceQuota = GB >> 10;
							quota = spaceQuota + "TB, " + numQuota;
						} else {
							spaceQuota = GB;
							quota = spaceQuota + "GB, " + numQuota;
						}
					}
				}
				map.put("quota", quota);
			} else {
				map.put("quota", "--, --");
			}
			resList.add(map);
		}
		return resList;

	}

	public static boolean isFileCountAvai(String path) throws Exception {
		FileSystem fs = getFileSystem();

		if (fs.exists(new Path(path))) {
			FileStatus fileStatus = fs.getFileStatus(new Path(path));
			if (fileStatus.isDirectory()) {
				ContentSummary c = fs.getContentSummary(fileStatus.getPath());
				long count = c.getFileCount() + c.getDirectoryCount();
				if (c.getQuota() > count)
					return true;
				else if (c.getQuota() < 0)
					return true;
				else
					return false;
			} else
				return false;

		} else
			return false;

	}

	public static Map getSpaceAndFileCount(String path) throws Exception {
		Map result = new HashMap<>();
		long count = 0;
		long space = 0;
		FileSystem fs = getFileSystem();

		if (fs.exists(new Path(path))) {
			FileStatus fileStatus = fs.getFileStatus(new Path(path));
			if (fileStatus.isDirectory()) {
				ContentSummary c = fs.getContentSummary(fileStatus.getPath());
				count = c.getFileCount() + c.getDirectoryCount();
				space = c.getSpaceConsumed();
				result.put("space", space);
				result.put("count", count);
			} else
				return null;
		} else
			return null;

		return result;
	}
}
