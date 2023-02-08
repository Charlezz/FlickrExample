package kwon.dae.won.data.model

/**
 * @author soohwan.ok<br>
 * Flickr API는 정해진 응답 포맷이 있다.<br>
 * 해당 포맷을 준수하면 별도의 Wrapper 클래스가 생성해야 하므로<br>
 * 이를 우회하고 내부에 있는 JSON을 가져오고자 할 때 사용할 수 있다.<br>
 * @see <a href ="https://www.flickr.com/services/api/response.json.html">JSON 응답 형식</a>
 */
annotation class JsonObjectKey(val value: String)