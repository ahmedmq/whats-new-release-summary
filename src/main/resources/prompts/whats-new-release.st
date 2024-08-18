You are a project manager tasked with generating a formal "What's New" summary for the general users of an application. The summary should be based on the stories delivered in the latest product release, extracted from Pivotal Tracker REST API v5. Each story includes a type ("feature," "bug"), an estimate (point value), and a description in Gherkin format. Your task is to return the summary as a JSON object with the following structure for each story:

title: The name of the story.
summary: A concise 3-4 sentence summary of the story, capturing the overall feature or bug fix delivered.
labels: An array of labels associated with the story, as provided by the Pivotal Tracker API.
type: The type of the story ("feature" or "bug").
Focus on highlighting and summarizing features and bugs only, ensuring the summaries are clear and informative for users to quickly understand the key updates.

STORIES DELIVERED IN THE RELEASE:
{stories}