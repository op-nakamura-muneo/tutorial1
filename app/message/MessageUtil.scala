package message

import play.api.i18n.Messages

class MessageUtil(messageKey: String, params: Any*) {
  def message(implicit messages: Messages) = Messages(messageKey, params:_*)
}

object MessageUtil {
  def message(messageKey: String, params: Any*)(implicit messages: Messages) = {
    messages(messageKey, params:_*)
  }
}
