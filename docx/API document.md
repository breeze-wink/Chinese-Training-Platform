# 接口文档

## Student

### Login

- **接口路径**：`/api/student/login`
- **请求方法**：POST
- **接口说明**：学生用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误"
    }
    ```

### Send Verification Code

- **接口路径**：`/api/student/send-verification`
- **请求方法**：POST
- **接口说明**：学生用户通过邮箱发送验证码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string" // 学生的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "验证码已发送",
      "verificationCode" : "string"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "邮箱格式不正确"
    }
    ```

### Register

- **接口路径**：`/api/student/register`
- **请求方法**：POST
- **接口说明**：学生用户通过邮箱和验证码进行注册。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string", // 学生的邮箱地址
      "verificationCode" : "string", // 验证码
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`201 Created`):
    ```json
    {
      "message" : "注册成功",
      "id" : 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "验证码错误或已过期"
    }
    ```

### Get Student Info

- **接口路径**：`/api/student/{id}`
- **请求方法**：GET
- **接口说明**：学生用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "id": 12345,
      "username": "string",
      "email": "string",
      "name": "string",
      "grade": 12,
      "schoolId": 6789
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到"
    }
    ```

## Teacher

### Login

- **接口路径**：`/api/teacher/login`
- **请求方法**：POST
- **接口说明**：教师用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误"
    }
    ```

### Send Verification Code

- **接口路径**：`/api/teacher/send-verification`
- **请求方法**：POST
- **接口说明**：教师用户通过邮箱发送验证码。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "authorizationCode": "string", // 授权码
      "email" : "string" // 教师的邮箱地址
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "验证码已发送",
      "verificationCode" : "string"
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "邮箱格式不正确"
    }
    ```

### Register

- **接口路径**：`/api/teacher/register`
- **请求方法**：POST
- **接口说明**：教师用户通过邮箱、密码进行注册。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "email" : "string", // 教师的邮箱地址
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`201 Created`):
    ```json
    {
      "message" : "注册成功",
      "id" : 12345
    }
    ```
  - **失败响应** (`400 Bad Request`):
    ```json
    {
      "message" : "验证码或授权码错误"
    }
    ```

### Get Teacher Info

- **接口路径**：`/api/teacher/{id}`
- **请求方法**：GET
- **接口说明**：教师用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "id": 12345,
      "name": "string",
      "email": "string",
      "phoneNumber": "string",
      "schoolId": 6789
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到"
    }
    ```

## SystemAdmin

### Login `finished`

- **接口路径**：`/api/system-admin/login`
- **请求方法**：POST
- **接口说明**：系统管理员用户通过账号和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误"
    }
    ```

### Get SystemAdmin Info `finished`

- **接口路径**：`/api/system-admin/{id}`
- **请求方法**：GET
- **接口说明**：系统管理员用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "id": 12345,
      "username": "string",
      "email": "string"
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到"
    }
    ```

## SchoolAdmin

### Login

- **接口路径**：`/api/school-admin/login`
- **请求方法**：POST
- **接口说明**：学校管理员用户通过用户名和密码进行登录。
- **请求说明**
  - 请求头: `Content-Type` : `application/json`
  - 请求参数:
    - 请求体(`JSON` 格式)：
    ```json
    {
      "account" : "string", // 用户名或邮箱
      "password" : "string" // 密码
    }
    ```
- **响应说明**
  - 响应格式: `JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "message" : "success",
      "id" : 12345
    }
    ```
  - **失败响应** (`401 Unauthorized`):
    ```json
    {
      "message" : "用户名或密码错误"
    }
    ```

### Get SchoolAdmin Info

- **接口路径**：`/api/school-admin/{id}`
- **请求方法**：GET
- **接口说明**：学校管理员用户通过用户 ID 获取自己的信息。
- **请求说明**：
  - 请求参数：
    - 路径参数（Path Variable）：`id` - 用户的唯一标识符

- **响应说明**：
  - 响应格式：`JSON`
  - **成功响应** (`200 OK`):
    ```json
    {
      "id": 12345,
      "username": "string",
      "email": "string",
      "schoolId": 6789
    }
    ```
  - **失败响应** (`404 Not Found`):
    ```json
    {
      "message": "用户未找到"
    }
    ```
