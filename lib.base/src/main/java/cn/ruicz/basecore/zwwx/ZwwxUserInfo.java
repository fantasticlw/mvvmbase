package cn.ruicz.basecore.zwwx;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by fsh on 2018/11/24.
 */

public class ZwwxUserInfo {

    /**
     * msg : SUCCESS
     * code : 0
     * data : {"headPicture":"","userStatus":1,"sex":1,"mobile":0,"id":8179689,"position":"","userName":"ruice8","departmentList":[{"id":145619,"account":"ruice8","departmentId":4389,"departmentName":"省厅-地市测试部门","mainDuty":null,"duties":"","orderNumber":147456,"orderUpdateTime":null,"topOrder":0,"syncStatus":3,"top":false,"mainDepartment":false}],"userId":"ruice8","email":"","extAttr":[]}
     * acode : 43303046-9585-4110-aabe-f2dcee0be8df
     */

    private String msg;
    private String code;
    private DataBean data;
    private String acode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    public static class DataBean {
        /**
         * headPicture :
         * userStatus : 1
         * sex : 1
         * mobile : 0
         * id : 8179689
         * position :
         * userName : ruice8
         * departmentList : [{"id":145619,"account":"ruice8","departmentId":4389,"departmentName":"省厅-地市测试部门","mainDuty":null,"duties":"","orderNumber":147456,"orderUpdateTime":null,"topOrder":0,"syncStatus":3,"top":false,"mainDepartment":false}]
         * userId : ruice8
         * email :
         * extAttr : []
         */

        private String headPicture;
        private String userStatus;
        private String sex;
        private String mobile;
        private String id;
        private String position;
        private String userName;
        private String userId;
        private String email;
        private List<DepartmentListBean> departmentList;
        private List<ExtAttr> extAttr;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<DepartmentListBean> getDepartmentList() {
            return departmentList;
        }

        public void setDepartmentList(List<DepartmentListBean> departmentList) {
            this.departmentList = departmentList;
        }

        public List<ExtAttr> getExtAttr() {
            return extAttr;
        }

        public void setExtAttr(List<ExtAttr> extAttr) {
            this.extAttr = extAttr;
        }

        public String getPoliceId(){
            for (ExtAttr temp : extAttr){
                if (TextUtils.equals(temp.externalKey, "警号")){
                    return temp.externalValue;
                }
            }
            return null;
        }

        public String getJobStatus(){
            for (ExtAttr temp : extAttr){
                if (TextUtils.equals(temp.externalKey, "人员状态")){
                    return temp.externalValue;
                }
            }
            return null;
        }

        public String getTerminalPhoneNumber(){
            for (ExtAttr temp : extAttr){
                if (TextUtils.equals(temp.externalKey, "终端号码")){
                    return temp.externalValue;
                }
            }
            return null;
        }

        public String getOrganizationId(){
            for (ExtAttr temp : extAttr){
                if (TextUtils.equals(temp.externalKey, "机构代码")){
                    return temp.externalValue;
                }
            }
            return null;
        }

        public static class DepartmentListBean {
            /**
             * id : 145619
             * account : ruice8
             * departmentId : 4389
             * departmentName : 省厅-地市测试部门
             * mainDuty : null
             * duties :
             * orderNumber : 147456
             * orderUpdateTime : null
             * topOrder : 0
             * syncStatus : 3
             * top : false
             * mainDepartment : false
             */

            private String id;
            private String account;
            private String departmentId;
            private String departmentName;
            private Object mainDuty;
            private String duties;
            private String orderNumber;
            private Object orderUpdateTime;
            private String topOrder;
            private String syncStatus;
            private boolean top;
            private boolean mainDepartment;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
                this.departmentId = departmentId;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            public Object getMainDuty() {
                return mainDuty;
            }

            public void setMainDuty(Object mainDuty) {
                this.mainDuty = mainDuty;
            }

            public String getDuties() {
                return duties;
            }

            public void setDuties(String duties) {
                this.duties = duties;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public Object getOrderUpdateTime() {
                return orderUpdateTime;
            }

            public void setOrderUpdateTime(Object orderUpdateTime) {
                this.orderUpdateTime = orderUpdateTime;
            }

            public String getTopOrder() {
                return topOrder;
            }

            public void setTopOrder(String topOrder) {
                this.topOrder = topOrder;
            }

            public String getSyncStatus() {
                return syncStatus;
            }

            public void setSyncStatus(String syncStatus) {
                this.syncStatus = syncStatus;
            }

            public boolean isTop() {
                return top;
            }

            public void setTop(boolean top) {
                this.top = top;
            }

            public boolean isMainDepartment() {
                return mainDepartment;
            }

            public void setMainDepartment(boolean mainDepartment) {
                this.mainDepartment = mainDepartment;
            }
        }

        public static class ExtAttr{
            String id;
            String personId;
            String externalKey;
            String externalValue;
        }
    }
}
