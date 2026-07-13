# 17 — Cloud Fundamentals (AWS)

This module mixes two kinds of artifact:

1. **Runnable code** — a Java Lambda (`lambda/`), a SAM template (`sam-template.yaml`), and a CloudFormation template (`cloudformation/s3-dynamodb.yaml`) that could actually be built/deployed with the AWS CLI and SAM CLI.
2. **Console-walkthrough documentation** (this README) — step-by-step click-paths for the parts of the AWS Cloud Fundamentals syllabus that are learned in the AWS Management Console (EC2, VPC, RDS) rather than as source code. These steps are written to be followed by hand in the console; they are **not executed** as part of this repository.

## Files in This Module

- [`lambda/pom.xml`](./lambda/pom.xml) — Maven build for the Lambda.
- [`lambda/src/main/java/com/dn5/week6/cloud/HelloLambdaHandler.java`](./lambda/src/main/java/com/dn5/week6/cloud/HelloLambdaHandler.java) — the handler.
- [`lambda/src/test/java/com/dn5/week6/cloud/HelloLambdaHandlerTest.java`](./lambda/src/test/java/com/dn5/week6/cloud/HelloLambdaHandlerTest.java) — JUnit 5 unit tests.
- [`sam-template.yaml`](./sam-template.yaml) — SAM template wiring the Lambda to API Gateway `GET /hello`.
- [`cloudformation/s3-dynamodb.yaml`](./cloudformation/s3-dynamodb.yaml) — S3 bucket + DynamoDB table.

---

## Part A: Launching an EC2 Instance (Console Walkthrough)

1. Sign in to the AWS Console and navigate to **EC2 > Instances > Launch instances**.
2. **Name**: enter `dn5-week6-web`.
3. **Application and OS Images (AMI)**: choose **Amazon Linux 2023** (free-tier eligible).
4. **Instance type**: choose **t2.micro** (free-tier eligible; sufficient for a demo web server).
5. **Key pair (login)**: click **Create new key pair**, name it `dn5-week6-keypair`, type **RSA**, format **.pem** (or **.ppk** for PuTTY on Windows). Download and store the private key securely — it cannot be re-downloaded.
6. **Network settings**: click **Edit**, and:
   - Select the VPC created in Part B (or the default VPC for a quick demo).
   - Enable **Auto-assign public IP**.
   - Under **Firewall (security groups)**, choose **Create security group** named `dn5-week6-web-sg` with these inbound rules:
     - Type **SSH**, Port 22, Source **My IP** (not `0.0.0.0/0`, to avoid exposing SSH to the internet).
     - Type **HTTP**, Port 80, Source **Anywhere (0.0.0.0/0)**.
7. **Configure storage**: leave the default 8 GiB gp3 volume.
8. Click **Launch instance**, then **View all instances** and wait until **Instance state** shows **Running** and **Status check** shows **2/2 checks passed**.
9. **Connect via SSH**:
   - Select the instance, click **Connect > SSH client**, and copy the example command shown.
   - From a terminal where the `.pem` file lives, run:
     ```bash
     chmod 400 dn5-week6-keypair.pem
     ssh -i dn5-week6-keypair.pem ec2-user@<Public-IPv4-DNS>
     ```
   - Alternatively, use **EC2 Instance Connect** (browser-based SSH) directly from the console without managing key files locally.

---

## Part B: Creating a VPC with Public + Private Subnets

1. Navigate to **VPC > Your VPCs > Create VPC**.
2. Choose **VPC and more** (the wizard) so subnets, route tables, and an Internet Gateway are created together:
   - **Name tag**: `dn5-week6-vpc`.
   - **IPv4 CIDR**: `10.0.0.0/16`.
   - **Number of Availability Zones**: 2 (for the RDS Multi-AZ setup in Part C).
   - **Number of public subnets**: 2 (e.g., `10.0.0.0/24`, `10.0.1.0/24`).
   - **Number of private subnets**: 2 (e.g., `10.0.10.0/24`, `10.0.11.0/24`).
   - **NAT gateways**: 1 per AZ (or "None" to save cost for a pure demo, but private-subnet outbound internet access requires it).
   - **VPC endpoints**: none needed for this exercise.
3. Click **Create VPC**. The wizard automatically creates:
   - An **Internet Gateway**, attached to the VPC.
   - A **public route table** with a `0.0.0.0/0` route pointing at the Internet Gateway, associated with both public subnets.
   - **Private route table(s)** with routes to the NAT Gateway (if selected) for outbound-only internet access.
4. Verify under **Route tables**: the public route table has a route to `igw-xxxxxxxx`; the private route table(s) have a route to `nat-xxxxxxxx` (or no default route if NAT was skipped).

---

## Part C: Creating an RDS MySQL Instance (Multi-AZ, Private Subnet)

1. Navigate to **RDS > Databases > Create database**.
2. **Choose a database creation method**: **Standard create**.
3. **Engine options**: **MySQL**, version 8.0 (latest minor).
4. **Templates**: choose **Production** (this enables Multi-AZ options).
5. **Settings**:
   - **DB instance identifier**: `dn5-week6-bookstore-db`.
   - **Master username**: `admin`; let AWS auto-generate the master password (retrieve it later from Secrets Manager) or set one manually.
6. **Instance configuration**: **Burstable classes**, `db.t3.medium` (or smaller for a demo).
7. **Availability & durability**: select **Multi-AZ DB instance** (creates a standby replica in a second AZ for automatic failover).
8. **Connectivity**:
   - **Virtual private cloud (VPC)**: `dn5-week6-vpc` from Part B.
   - **DB subnet group**: create a new one restricted to the **private subnets** only.
   - **Public access**: **No** (keeps the database unreachable from the internet).
   - **VPC security group**: create new, named `dn5-week6-rds-sg`, with an inbound rule allowing **MySQL/Aurora, port 3306**, source = the EC2 security group (`dn5-week6-web-sg`) — not a public CIDR.
9. **Additional configuration**: set an initial database name `bookstore`, enable automated backups (7-day retention), and enable encryption at rest.
10. Click **Create database** and wait for status **Available**. Only resources inside the VPC (e.g., the EC2 instance from Part A, or a Lambda with matching VPC config) can reach it on port 3306.

---

## Part D: Deploying the SAM Template

The Lambda in `lambda/` is packaged and deployed with the AWS SAM CLI (run from a machine with the AWS CLI and SAM CLI installed and credentials configured — not executed in this repository):

```bash
cd 17-Cloud-Fundamentals-AWS
mvn -f lambda/pom.xml clean package        # produces lambda/target/lambda-0.0.1.jar
sam build --template-file sam-template.yaml
sam deploy --guided
```

`sam deploy --guided` prompts for:
- Stack name (e.g., `dn5-week6-hello-stack`).
- AWS Region.
- Confirmation of IAM role creation for the Lambda's execution role.
- Whether to save these answers to `samconfig.toml` for future non-guided deploys.

After deployment, SAM prints the API Gateway invoke URL from the `HelloApiUrl` output; hitting `<url>?name=Ada` returns `{"message": "Hello, Ada!"}`.

## Part E: Deploying the CloudFormation Template

The S3 + DynamoDB template is deployed directly with the AWS CLI:

```bash
aws cloudformation deploy \
  --template-file cloudformation/s3-dynamodb.yaml \
  --stack-name dn5-demo-stack \
  --capabilities CAPABILITY_IAM
```

This creates (or updates, if the stack already exists) the `dn5-week6-bookstore-assets-*` S3 bucket with versioning enabled and the `dn5-week6-bookstore-table` DynamoDB table with an `id` (String) partition key on pay-per-request billing.

---

**Note:** Parts A-C are AWS console click-paths meant to be followed manually in a real AWS account; they cannot run inside this repository or CI. Parts D-E reference real, deployable IaC files checked into this module.
