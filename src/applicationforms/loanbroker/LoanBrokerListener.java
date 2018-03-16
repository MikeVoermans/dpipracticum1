package applicationforms.loanbroker;

import applicationforms.abnamro.bank.JMSBankFrame;
import mix.model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class LoanBrokerListener implements MessageListener {

    private LoanBrokerFrame loanBrokerFrame;
    private JMSBankFrame jmsBankFrame;

    public LoanBrokerListener(LoanBrokerFrame loanBrokerFrame, JMSBankFrame jmsBankFrame) {
        this.loanBrokerFrame = loanBrokerFrame;
        this.jmsBankFrame = jmsBankFrame;
    }

    public void onMessage(Message message) {
        ObjectMessage textMessage = (ObjectMessage) message;
        Object object = null;
        try {
            object = textMessage.getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (loanBrokerFrame != null) {
            loanBrokerFrame.add((LoanRequest) object);
        }
        if (jmsBankFrame != null) {
            jmsBankFrame.add((LoanRequest) object);
        }
    }
}
