package com.example

import software.amazon.awscdk.core.*
import software.amazon.awscdk.services.iam.*

class SnykUserStack @JvmOverloads constructor(app: App, id: String, props: StackProps? = null) :

    Stack(app, id, props) {
  init {

    val stmt = PolicyStatement(PolicyStatementProps.builder()
        .effect(Effect.ALLOW)
        .actions(listOf(
            "ecr:GetLifecyclePolicyPreview",
            "ecr:GetDownloadUrlForLayer",
            "ecr:BatchGetImage",
            "ecr:DescribeImages",
            "ecr:GetAuthorizationToken",
            "ecr:DescribeRepositories",
            "ecr:ListTagsForResource",
            "ecr:ListImages",
            "ecr:BatchCheckLayerAvailability",
            "ecr:GetRepositoryPolicy",
            "ecr:GetLifecyclePolicy"
        ))
        .resources(listOf("*"))
        .build()
    )

    val policyDocument = PolicyDocument(PolicyDocumentProps.builder()
        .assignSids(true)
        .statements(listOf(stmt))
        .build()
    )

    val SNYK_PRINCIPAL_ARN = "arn:aws:iam::198361731867:user/ecr-integration-user"

    // from cdk.json
    val snykOrganizationId = this.node.tryGetContext("SNYK_ORGANIZATION_ID").toString()

    val role = Role(this, "snyk-integration-role", RoleProps.builder()
        .roleName("SnykServiceRole")
        .assumedBy(ArnPrincipal(SNYK_PRINCIPAL_ARN).withConditions(mapOf(
            "StringEquals" to mapOf(
                "sts:ExternalId" to listOf(snykOrganizationId)
            )
        )))
        .inlinePolicies(mapOf("SnykServiceRolePolicy" to policyDocument))
        .build()
    )

    CfnOutput(this, "SnykServiceRoleArn", CfnOutputProps.builder().value(role.roleArn).build())
  }
}