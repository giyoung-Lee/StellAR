interface ConstellationDetail {
  constellationId: string;
  constellationSeason: string;
  constellationDesc: string;
  constellationSubName: string;
  constellationStartObservation: string;
  constellationImg: string;
  constellationStory: string;
  constellationType: string;
  constellationEndObservation: string;
}

interface QuizType {
  constellationQuestionContents: string;
  constellationQuestionAnswer: string;
  constellationId: string;
}
