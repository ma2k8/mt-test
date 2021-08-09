# mt-test

n+1を500件 & そこで得た結果を半分にフィルタする処理をサンプルにパフォーマンステストを行う

## ruby3 + rails

- テスト方法

```
$ cd ruby3/
$ docker-compose up
$ curl http://localhost:3000/api/v1/users
$ curl http://localhost:3000/api/v1/users_thread
```

- 通常版
 Completed 200 OK in 3100ms

- parallel使用版
 Completed 200 OK in 2126ms
 
 ※Reactorは構文が微妙すぎたので対象から外した
 
 
 ## kotln

```
$ cd rere/
$ docker-compose up
$ make run
$ curl http://localhost:8080/user_filter/
$ curl http://localhost:8080/user_filter_async/
```

- 通常版
 Completed 200 OK in 700ms ~ 1500ms

- coroutine使用版
 Completed 200 OK in 16ms ~ 500ms
 
