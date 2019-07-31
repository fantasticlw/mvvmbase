package cn.ruicz.basecore.zwwx;

/**
 * cn.ruicz.basecore.zwwx
 *
 * @author xyq
 * @time 2019-7-31 10:43
 * Remark -----------旧版获取用户信息-------------------
 */
public class RczUserInfo {
    /**
     * code : 0
     * acode : 7fd28498-6ab8-411c-a9ce-3a0b4e4ce0e1
     * desc : 成功
     * data : {"jh":"ruice1","name":"ruice1","department":"accdc7d710f04253972ce0d35074e0f0","position":"","mobile":"null","gender":"1","extJh":"null","avatar":"0"}
     */

    private String code;
    private String acode;
    private String desc;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * jh : ruice1
         * name : ruice1
         * department : accdc7d710f04253972ce0d35074e0f0
         * position :
         * mobile : null
         * gender : 1
         * extJh : null
         * avatar : 0
         */

        private String jh;
        private String name;
        private String department;
        private String position;
        private String mobile;
        private String gender;
        private String extJh;
        private String avatar;

        public String getJh() {
            return jh;
        }

        public void setJh(String jh) {
            this.jh = jh;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getExtJh() {
            return extJh;
        }

        public void setExtJh(String extJh) {
            this.extJh = extJh;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
