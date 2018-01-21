package com.diyishuai.notebook.service;

import com.diyishuai.notebook.bean.HbaseData;
import com.diyishuai.notebook.bean.NoteBook;
import com.diyishuai.notebook.constants.Constants;
import com.diyishuai.notebook.dao.HbaseDao;
import com.diyishuai.notebook.dao.RedisDao;
import com.google.gson.JsonArray;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteBookService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private HbaseDao hbaseDao;

    public List<NoteBook> getAllByUserId(Integer userId) throws IOException {
        List<NoteBook> noteBooks = new ArrayList<>();
        //先查redis
        List<String> list = redisDao.getList(userId.toString());
        if (noteBooks!=null && !noteBooks.isEmpty()){
            for (String noteStr : list) {
                NoteBook noteBook = new NoteBook();
                String[] noteArr = noteStr.split("\\"+Constants.STRING_SEPARATOR);
                noteBook.setRowKey(noteArr[0]);
                noteBook.setName(noteArr[1]);
                noteBook.setCreateTime(noteArr[2]);
                noteBook.setStatus(noteArr[3]);

                noteBooks.add(noteBook);
            }
        }else{
            //如果redis中没有，去查hbase
            ResultScanner results = hbaseDao.queryByReg(Constants.NOTEBOOK_TABLE_NAME, userId.toString() + "*");
            for (Result result : results) {
                noteBooks.add(
                    new NoteBook(
                        Bytes.toString(result.getRow()),//rowkey
                        new String(result.getValue(     //name
                                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_NOTEBOOKNAME.getBytes())
                        ),
                        new String(result.getValue(     //createtime
                                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_CREATETIME.getBytes())
                        ),
                        new String(result.getValue(     //status
                                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_STATUS.getBytes())
                        )
                    )
                );

            }
            //将hbase中的信息加载到redis中
            if (!noteBooks.isEmpty()){
                noteBooks.stream().forEach(
                        noteBook -> redisDao.appendRightList(userId.toString(), noteBook.toString())
                );
            };
        }
        return noteBooks;
    }

    public boolean save(NoteBook noteBook) {
        boolean isSuccess = false;
        //1 redis保存
        boolean redisIsSuccess = redisDao.appendRightList(noteBook.getRowKey(), noteBook.toString());
        if (redisIsSuccess){
            //2 hbase保存
            List<HbaseData> data = noteBook2HbaseDataList(noteBook);
            String rowKey = noteBook.getRowKey()+Constants.ROWKEY_SEPARATOR+noteBook.getCreateTime();
            boolean hbaseIsSuccess = hbaseDao.insertData(Constants.NOTEBOOK_TABLE_NAME,
                    rowKey,
                    data);
            if (hbaseIsSuccess){
                isSuccess = true;
            }else {
                //hbase如果保存失败，删除redis中的数据
                redisDao.deleteValueOfListStartLeft(noteBook.getRowKey(),1, noteBook.toString());
            }
        }
        return isSuccess;
    }

    private List<HbaseData> noteBook2HbaseDataList(NoteBook noteBook) {
        List<HbaseData> data = new ArrayList<>();
        data.add(new HbaseData(
                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_NOTEBOOKNAME.getBytes(),
                    noteBook.getName().getBytes())
        );
        data.add(new HbaseData(
                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_CREATETIME.getBytes(),
                    noteBook.getCreateTime().getBytes())
        );
        data.add(new HbaseData(
                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_STATUS.getBytes(),
                    noteBook.getStatus().getBytes())
        );
        data.add(new HbaseData(
                    Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(),
                    Constants.NOTEBOOK_NOTEBOOKINFO_CLU_NOTELIST.getBytes(),
                    new JsonArray().toString().getBytes())
        );
        return data;
    }
}
