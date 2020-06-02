# snyk-aws-integration-user-cdk
create snyk role & policy on AWS by CDK

# Usage

Edit `cdk.json` and re-write `YOUR_SNYK_ORGANIZATION_ID`

```
$ cdk context
Context found in cdk.json:

┌───┬──────────────────────┬───────────────────────────────┐
│ # │ Key                  │ Value                         │
├───┼──────────────────────┼───────────────────────────────┤
│ 1 │ SNYK_ORGANIZATION_ID │ "<YOUR_SNYK_ORGANIZATION_ID>" │
└───┴──────────────────────┴───────────────────────────────┘
Run cdk context --reset KEY_OR_NUMBER to remove a context key. It will be refreshed on the next CDK synthesis run.
```

Then, dryrun cdk.

```
$ cdk diff
```

Finally, deploy to AWS.

```
$ cdk deploy
```
