# 🚀 Outven - 掲示板アーキテクチャのリファクタリングとセキュリティ強化

## 📌 プロジェクト概要
Outvenは、バックエンド構造とMVCアーキテクチャに慣れるための掲示板チームプロジェクトとして始まりました。  
当初は基本機能の実装に重点を置いていましたが、開発後に構造的な問題やセキュリティ上の脆弱性、保守性の難しさを実感し、全面的なリファクタリングを行いました。

2024年2月、非情報系出身者中心の4人チームで開発後、**約1ヶ月間かけて個人で全面リファクタリング**を実施しました。

## 🎯 リファクタリングの目的
- 重複したコントローラの統合と掲示板分岐戦略の適用
- セキュリティ問題の解決（セッション処理、平文パスワード、認証バイパスなど）
- Thymeleafベースのテンプレート構造の整理
- SRPに基づくサービス/ユーティリティ分離とテストの容易化

## ⚙️ システムアーキテクチャ
- バックエンド：Java 17, Spring Boot, Spring Security
- データベース：Oracle 18c XE, JPA
- セッション：Redis + Spring Session
- テンプレート：Thymeleaf
- デプロイ：Gradle + WAR + 外部Tomcat（現在はローカル専用）

## 🧩 主な技術適用と改善事例

### 掲示板構造の改善
- `BoardController`の単一化と`Enum`による分岐処理
- `Controller` → `Service` → `DTO/Entity` → `Repository` の階層構成

### 認証とセキュリティの改善
- パスワード：平文 → BCryptハッシュ化
- Spring Securityによる認証処理
- Redisベースのセッション管理 → 重複ログインの防止
- `ControllerAdvice`によるグローバル例外処理の導入

### パフォーマンス最適化
- `Pageable` + フェッチ戦略改善 → N+1問題の解消
- 掲示板ページングのレスポンス速度を約40%改善（ローカルでの1万件テスト基準）

## 📈 リファクタリング前後の比較

| 項目 | Before | After |
|------|--------|-------|
| 掲示板構造 | コントローラ複数実装 | BoardController + Enumによる統合 |
| セッション管理 | HttpSession | Spring Session + Redis |
| 認証/セキュリティ | 平文パスワード、認証バイパス | BCrypt, Spring Security, 認証URL保護 |
| テンプレート | Mustache | Thymeleaf Fragmentによる統合 |
| APIレスポンス | 非一貫性あり | ResponseEntityによる統一 |
| 例外処理 | try-catch中心 | ControllerAdviceによる全体処理 |
| ファイル処理 | Controllerに直接記述 | FileServiceとして分離 |
| コメント処理 | DAO方式 | REST API + 非同期UX向上 |

## 🧠 回顧と成長
チームプロジェクトの構造的な限界を感じ、自ら改善に取り組んだことで、  
セキュリティ、設計、保守性、性能といった実務で重要な観点を体験できました。  
この経験は、後続プロジェクト「별 헤는 밤（星を数える夜）」における構造設計や認証機能拡張の土台となりました。

---

## 🛠 技術スタック

| 分類 | 技術 |
|------|------|
| バックエンド | Java 17, Spring Boot, Spring Security, JPA, Lombok |
| フロントエンド | Thymeleaf, Bootstrap 5.3, JavaScript, jQuery |
| インフラ | Oracle 18c XE, Redis, STS4, Gradle, 外部Tomcat（WAR） |
| その他 | Ajax, BCrypt, ResponseEntity, ERD設計 |

---

## 📈 パフォーマンスおよび構造の改善指標

| 項目 | Before | After | 改善効果 |
|------|--------|-------|-----------|
| テンプレート描画速度 | 平均120ms | 70ms | Thymeleaf構造統合により約40%改善 |
| コントローラ数 | 15以上 | 11へ統合 | 重複排除により保守範囲が約27%減少 |
| コメントAPI構造 | 未実装またはController内 | `CommentRestController`で分離 | UX向上とSRP原則の適用 |
| 掲示板構造処理 | 掲示板ごとに個別実装 | `BoardController` + Enum活用 | 一貫性と拡張性の確保 |
| ファイル処理構造 | Controllerで直接処理 | FileServiceで分離 | テストや保守が容易に |
| コード階層構成 | 一部Service/DTOあり | DTO/Service/Entity/Validatorで分離 | テストや保守性が向上 |
| APIレスポンス形式 | 多様な返却形式 | `ResponseEntity`で統一 | 一貫性とドキュメント化が容易に |
| セキュリティ機能数 | 無し | 最低3種以上導入 | 認証・暗号化・セッション管理など実務標準に対応 |

> 📌 上記指標は、ローカル環境（32GB RAM, Ryzen 7 7800X, SSD）にてChrome DevToolsで測定

---

## 🧩 システムアーキテクチャ

![Outven アーキテクチャ](/docs/architecture.png)

Spring Bootをベースに、Controller、Service、Repository、Security、Redis、Oracle DB等を明確に分離  
→ 保守性と拡張性を強化

---

## 🗃 ERD（データベース構造図）

![Outven ERD](/docs/erd.png)

- ユーザー、掲示板、投稿、コメント、ロール、メール認証のテーブルで構成  
- `User` ↔ `Role`, `Post`, `Comment`, `EmailVerification` の関連  
- `Post` ↔ `Board` の連結構造

---

## 🚀 実行方法

```bash
git clone https://github.com/yourname/outven.git
cd outven
./gradlew clean build
```

> 📌 Oracle DBの設定が必要（application.yml または .properties）
> 📌 外部TomcatによるWAR形式のデプロイをサポート

---

## 📅 今後の改善予定

* Redisベースのメール認証状態の保存
* JUnit / Mockitoによるテストコードの拡充
* 掲示板のソート・フィルター条件の強化
* 管理者向け統計機能（活動ログ、ユーザー分析など）

---

## 🙌 感想と成長

非情報系出身者4人のチームで始めたOutvenは、設計未熟さゆえに機能が分散し、構造が複雑でした。  
プロジェクト終了後、**約1ヶ月間個人で全面リファクタリング**を行いました。

この過程で、Springベースのアーキテクチャ設計、セキュリティ対応、パフォーマンス改善を実践的に経験し、  
**一人でも実務レベルのWebプロジェクトを完遂できるという自信**と、**技術的な成長**を実感しました。
