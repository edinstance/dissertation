import Modal from "@/components/ui/Modal";

describe("<Modal />", () => {
  it("renders", () => {
    cy.mount(
      <Modal open={true} setOpen={() => {}}>
        <>
          <p>Test</p>
        </>
      </Modal>,
    );
  });
});
