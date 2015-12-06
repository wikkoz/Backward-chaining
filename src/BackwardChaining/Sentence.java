package BackwardChaining;

public class Sentence {

    private String sentence;
    private boolean negated = false;

    public Sentence(String sentence) {
        if(sentence.charAt(0)=='~') {
            negated = true;
            this.sentence = sentence.substring(1);
        }
        else
            this.sentence=sentence;
    }

    public Sentence(Sentence sentence) {
        this.sentence = sentence.getOnlySentence();
        this.negated = sentence.negated;
    }

    private String getOnlySentence() {return sentence;}

    public String getSentence() {
        return (negated ? "~" : "") + sentence;
    }

    public Sentence negate(){
        negated = ! negated;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence1 = (Sentence) o;

        return negated == sentence1.negated && sentence.equals(sentence1.sentence);

    }

    @Override
    public int hashCode() {
        int result = sentence.hashCode();
        result = 31 * result + (negated ? 1 : 0);
        return result;
    }
}
