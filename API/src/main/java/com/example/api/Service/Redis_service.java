package com.example.api.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Redis_service {
    public void setkey(String key, Object object);




    public Object getkey(String key);

    public  void delete_key(String key);
    public void settimetolive(String key,long time);




    //check hashkey
    public Boolean hasKey(String key, Object hashKey) ;

    //Lấy giá trị từ Redis dựa trên hashKey trong hash có key
    public Object get(String key, Object hashKey);

    //ấy nhiều giá trị từ Redis dựa trên một danh sách hashKeys trong hash có key
    public List<Object> multiGet(String key, Collection<String> hashKeys) ;



    //Lấy một cặp key-value ngẫu nhiên từ hash có key tương ứng
    public Map.Entry<String, Object> randomEntry(String key) ;

    //Lấy một danh sách các key ngẫu nhiên từ hash có
// key tương ứng trong Redis với số lượng là count
    public List<String> randomKeys(String key, long count);


    //Lấy một danh sách các cặp key-value ngẫu nhiên
// từ hash có key tương ứng trong Redis với số lượng là count,
    public Map<String, Object> randomEntries(String key, long count) ;

    //Lấy tất cả các key của hash có key tương ứng
    public Set<String> keys(String key) ;

    //
    public Long lengthOfValue(String key, String hashKey) ;


    public Long size(String key) ;


    public void putAll(String key, Map<? extends String,Object> m) ;


    public void put(String key, String hashKey, Object value) ;



    //Lấy tất cả các giá trị trong hash có key tương ứng
    public List<Object> values(String key) ;


    //Lấy tất cả các cặp key-value trong hash có key tương ứng
    public Map<String, Object> entries(String key) ;






}
